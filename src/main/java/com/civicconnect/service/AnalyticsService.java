package com.civicconnect.service;

import com.civicconnect.dto.AnalyticsSummaryDto;
import com.civicconnect.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public AnalyticsSummaryDto getAdminAnalytics() {
        long total = complaintRepository.count();

        List<Object[]> statusCounts = complaintRepository.countComplaintsByStatus();
        Map<String, Long> statusMap = new HashMap<>();
        long pending = 0, inProgress = 0, resolved = 0;

        for (Object[] row : statusCounts) {
            String status = (String) row[0];
            Long count = (Long) row[1];
            statusMap.put(status, count);
            if ("SUBMITTED".equals(status) || "ASSIGNED".equals(status)) {
                pending += count;
            } else if ("IN_PROGRESS".equals(status)) {
                inProgress += count;
            } else if ("RESOLVED".equals(status) || "CLOSED".equals(status)) {
                resolved += count;
            }
        }

        List<Object[]> categoryCounts = complaintRepository.countComplaintsByCategory();
        Map<String, Long> categoryMap = new HashMap<>();
        for (Object[] row : categoryCounts) {
            categoryMap.put((String) row[0], (Long) row[1]);
        }

        List<Object[]> wardCounts = complaintRepository.countComplaintsByWard();
        Map<String, Long> wardMap = new HashMap<>();
        for (Object[] row : wardCounts) {
            wardMap.put((String) row[0], (Long) row[1]);
        }

        return AnalyticsSummaryDto.builder()
                .totalComplaints(total)
                .pendingComplaints(pending)
                .inProgressComplaints(inProgress)
                .resolvedComplaints(resolved)
                .escalatedComplaints(complaintRepository.findByStatus("ESCALATED").size())
                .categoryDistribution(categoryMap)
                .statusDistribution(statusMap)
                .wardDistribution(wardMap)
                .build();
    }
}
