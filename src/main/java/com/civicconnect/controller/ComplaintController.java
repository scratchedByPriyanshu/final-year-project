package com.civicconnect.controller;

import com.civicconnect.dto.ComplaintDTOs.*;
import com.civicconnect.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<ComplaintResponse> createComplaint(@RequestBody ComplaintRequest request, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(complaintService.createComplaint(request, username));
    }

    @GetMapping("/my-complaints")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(complaintService.getCitizenComplaints(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponse> getComplaintById(@PathVariable Long id) {
        return ResponseEntity.ok(complaintService.getComplaintById(id));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<ComplaintResponse> verifyResolution(@PathVariable Long id, 
                                                               @RequestParam String action, 
                                                               @RequestParam(required = false) String comments,
                                                               Authentication authentication) {
        String status = "confirm".equalsIgnoreCase(action) ? "CLOSED" : "REOPENED";
        StatusUpdateRequest updateReq = new StatusUpdateRequest();
        updateReq.setStatus(status);
        updateReq.setComments(comments != null ? comments : ("Citizen action: " + status));
        return ResponseEntity.ok(complaintService.updateStatus(id, updateReq, authentication.getName()));
    }
}
