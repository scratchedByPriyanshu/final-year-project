package com.civicconnect.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "complaint_number", nullable = false, unique = true)
    private String complaintNumber;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;

    @ManyToOne
    @JoinColumn(name = "citizen_id", nullable = false)
    private User citizen;

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private User officer;

    @Column(nullable = false)
    private String status; // SUBMITTED, ASSIGNED, IN_PROGRESS, RESOLVED, CLOSED, REOPENED

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Lob
    @Column(name = "photo_url", columnDefinition = "LONGTEXT")
    private String photoUrl;

    @Column(name = "sla_deadline", nullable = false)
    private LocalDateTime slaDeadline;

    @Column(name = "is_escalated")
    private Boolean isEscalated;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Complaint() {}

    public Complaint(Long id, String complaintNumber, String title, String description, Category category, Department department, Ward ward, User citizen, User officer, String status, Double latitude, Double longitude, String address, String photoUrl, LocalDateTime slaDeadline, Boolean isEscalated, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.complaintNumber = complaintNumber;
        this.title = title;
        this.description = description;
        this.category = category;
        this.department = department;
        this.ward = ward;
        this.citizen = citizen;
        this.officer = officer;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.photoUrl = photoUrl;
        this.slaDeadline = slaDeadline;
        this.isEscalated = isEscalated;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.isEscalated == null) {
            this.isEscalated = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getComplaintNumber() { return complaintNumber; }
    public void setComplaintNumber(String complaintNumber) { this.complaintNumber = complaintNumber; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public Ward getWard() { return ward; }
    public void setWard(Ward ward) { this.ward = ward; }

    public User getCitizen() { return citizen; }
    public void setCitizen(User citizen) { this.citizen = citizen; }

    public User getOfficer() { return officer; }
    public void setOfficer(User officer) { this.officer = officer; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public LocalDateTime getSlaDeadline() { return slaDeadline; }
    public void setSlaDeadline(LocalDateTime slaDeadline) { this.slaDeadline = slaDeadline; }

    public Boolean getIsEscalated() { return isEscalated; }
    public void setIsEscalated(Boolean isEscalated) { this.isEscalated = isEscalated; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static ComplaintBuilder builder() { return new ComplaintBuilder(); }

    public static class ComplaintBuilder {
        private Long id;
        private String complaintNumber;
        private String title;
        private String description;
        private Category category;
        private Department department;
        private Ward ward;
        private User citizen;
        private User officer;
        private String status;
        private Double latitude;
        private Double longitude;
        private String address;
        private String photoUrl;
        private LocalDateTime slaDeadline;
        private Boolean isEscalated;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ComplaintBuilder id(Long id) { this.id = id; return this; }
        public ComplaintBuilder complaintNumber(String complaintNumber) { this.complaintNumber = complaintNumber; return this; }
        public ComplaintBuilder title(String title) { this.title = title; return this; }
        public ComplaintBuilder description(String description) { this.description = description; return this; }
        public ComplaintBuilder category(Category category) { this.category = category; return this; }
        public ComplaintBuilder department(Department department) { this.department = department; return this; }
        public ComplaintBuilder ward(Ward ward) { this.ward = ward; return this; }
        public ComplaintBuilder citizen(User citizen) { this.citizen = citizen; return this; }
        public ComplaintBuilder officer(User officer) { this.officer = officer; return this; }
        public ComplaintBuilder status(String status) { this.status = status; return this; }
        public ComplaintBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
        public ComplaintBuilder longitude(Double longitude) { this.longitude = longitude; return this; }
        public ComplaintBuilder address(String address) { this.address = address; return this; }
        public ComplaintBuilder photoUrl(String photoUrl) { this.photoUrl = photoUrl; return this; }
        public ComplaintBuilder slaDeadline(LocalDateTime slaDeadline) { this.slaDeadline = slaDeadline; return this; }
        public ComplaintBuilder isEscalated(Boolean isEscalated) { this.isEscalated = isEscalated; return this; }
        public ComplaintBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ComplaintBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Complaint build() {
            return new Complaint(id, complaintNumber, title, description, category, department, ward, citizen, officer, status, latitude, longitude, address, photoUrl, slaDeadline, isEscalated, createdAt, updatedAt);
        }
    }
}
