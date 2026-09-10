package com.civicconnect.controller;

import com.civicconnect.dto.AnalyticsSummaryDto;
import com.civicconnect.dto.ComplaintDTOs.ComplaintResponse;
import com.civicconnect.dto.ComplaintDTOs.StatusUpdateRequest;
import com.civicconnect.service.AnalyticsService;
import com.civicconnect.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/analytics")
    public ResponseEntity<AnalyticsSummaryDto> getAnalytics() {
        return ResponseEntity.ok(analyticsService.getAdminAnalytics());
    }

    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @PutMapping("/complaints/{id}/assign")
    public ResponseEntity<ComplaintResponse> assignDepartmentAndOfficer(@PathVariable Long id,
                                                                          @RequestBody StatusUpdateRequest request,
                                                                          Authentication authentication) {
        request.setStatus("ASSIGNED");
        return ResponseEntity.ok(complaintService.updateStatus(id, request, authentication.getName()));
    }
}
