package com.civicconnect.controller;

import com.civicconnect.dto.ComplaintDTOs.*;
import com.civicconnect.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/officer/complaints")
@CrossOrigin(origins = "*")
public class OfficerController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> getAllAssignedComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ComplaintResponse> updateComplaintStatus(@PathVariable Long id,
                                                                   @RequestBody StatusUpdateRequest request,
                                                                   Authentication authentication) {
        return ResponseEntity.ok(complaintService.updateStatus(id, request, authentication.getName()));
    }
}
