package com.civicconnect.service;

import com.civicconnect.dto.ComplaintDTOs.*;
import com.civicconnect.entity.*;
import com.civicconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusHistoryRepository statusHistoryRepository;

    @Transactional
    public ComplaintResponse createComplaint(ComplaintRequest request, String citizenUsername) {
        User citizen = userRepository.findByUsername(citizenUsername)
                .orElseThrow(() -> new RuntimeException("Citizen user not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Ward ward = wardRepository.findById(request.getWardId())
                .orElseThrow(() -> new RuntimeException("Ward not found"));

        // Auto-assign department based on category name
        Department department = mapCategoryToDepartment(category.getName());

        String complaintNumber = "CMP-" + LocalDateTime.now().getYear() + "-" + (1000 + (int)(Math.random() * 9000));
        LocalDateTime slaDeadline = LocalDateTime.now().plusHours(category.getDefaultSlaHours());

        Complaint complaint = Complaint.builder()
                .complaintNumber(complaintNumber)
                .title(request.getTitle())
                .description(request.getDescription())
                .category(category)
                .department(department)
                .ward(ward)
                .citizen(citizen)
                .status("SUBMITTED")
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .address(request.getAddress())
                .photoUrl(request.getPhotoUrl())
                .slaDeadline(slaDeadline)
                .isEscalated(false)
                .build();

        Complaint savedComplaint = complaintRepository.save(complaint);

        // Record initial timeline entry
        StatusHistory initialHistory = StatusHistory.builder()
                .complaint(savedComplaint)
                .status("SUBMITTED")
                .updatedBy(citizen)
                .comments("Complaint submitted by citizen.")
                .build();

        statusHistoryRepository.save(initialHistory);

        return mapToResponse(savedComplaint);
    }

    @Transactional
    public ComplaintResponse updateStatus(Long complaintId, StatusUpdateRequest request, String updatedByUsername) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        User updatedBy = userRepository.findByUsername(updatedByUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getOfficerId() != null) {
            User officer = userRepository.findById(request.getOfficerId()).orElse(null);
            complaint.setOfficer(officer);
        }

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId()).orElse(null);
            complaint.setDepartment(department);
        }

        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            complaint.setStatus(request.getStatus().toUpperCase());
        }

        Complaint updatedComplaint = complaintRepository.save(complaint);

        StatusHistory history = StatusHistory.builder()
                .complaint(updatedComplaint)
                .status(updatedComplaint.getStatus())
                .updatedBy(updatedBy)
                .comments(request.getComments())
                .photoProof(request.getPhotoProof())
                .build();

        statusHistoryRepository.save(history);

        return mapToResponse(updatedComplaint);
    }

    public List<ComplaintResponse> getCitizenComplaints(String username) {
        User citizen = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return complaintRepository.findByCitizenIdOrderByCreatedAtDesc(citizen.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ComplaintResponse> getAllComplaints() {
        return complaintRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ComplaintResponse getComplaintById(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        return mapToResponse(complaint);
    }

    private Department mapCategoryToDepartment(String categoryName) {
        String deptName = "Roads & Infrastructure";
        if (categoryName.contains("Garbage") || categoryName.contains("Waste")) {
            deptName = "Sanitation & Solid Waste";
        } else if (categoryName.contains("Light") || categoryName.contains("Electrical")) {
            deptName = "Electrical & Lighting";
        } else if (categoryName.contains("Water") || categoryName.contains("Drainage")) {
            deptName = "Water & Sewage";
        }
        return departmentRepository.findByName(deptName).orElse(null);
    }

    private ComplaintResponse mapToResponse(Complaint c) {
        List<TimelineItemDto> timeline = statusHistoryRepository.findByComplaintIdOrderByCreatedAtAsc(c.getId()).stream()
                .map(h -> TimelineItemDto.builder()
                        .id(h.getId())
                        .status(h.getStatus())
                        .updatedByName(h.getUpdatedBy() != null ? h.getUpdatedBy().getFullName() : "System")
                        .comments(h.getComments())
                        .photoProof(h.getPhotoProof())
                        .createdAt(h.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return ComplaintResponse.builder()
                .id(c.getId())
                .complaintNumber(c.getComplaintNumber())
                .title(c.getTitle())
                .description(c.getDescription())
                .categoryName(c.getCategory() != null ? c.getCategory().getName() : "General")
                .departmentName(c.getDepartment() != null ? c.getDepartment().getName() : "Unassigned")
                .wardName(c.getWard() != null ? c.getWard().getWardName() : "General Ward")
                .wardNumber(c.getWard() != null ? c.getWard().getWardNumber() : 101)
                .citizenName(c.getCitizen() != null ? c.getCitizen().getFullName() : "Anonymous")
                .officerName(c.getOfficer() != null ? c.getOfficer().getFullName() : "Unassigned")
                .status(c.getStatus())
                .latitude(c.getLatitude())
                .longitude(c.getLongitude())
                .address(c.getAddress())
                .photoUrl(c.getPhotoUrl())
                .slaDeadline(c.getSlaDeadline())
                .isEscalated(c.getIsEscalated() != null ? c.getIsEscalated() : false)
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .timeline(timeline)
                .build();
    }
}
