package com.civicconnect.dto;

import java.util.Map;

public class AnalyticsSummaryDto {
    private long totalComplaints;
    private long pendingComplaints;
    private long inProgressComplaints;
    private long resolvedComplaints;
    private long escalatedComplaints;
    private Map<String, Long> categoryDistribution;
    private Map<String, Long> statusDistribution;
    private Map<String, Long> wardDistribution;

    public AnalyticsSummaryDto() {}
    public AnalyticsSummaryDto(long totalComplaints, long pendingComplaints, long inProgressComplaints, long resolvedComplaints, long escalatedComplaints, Map<String, Long> categoryDistribution, Map<String, Long> statusDistribution, Map<String, Long> wardDistribution) {
        this.totalComplaints = totalComplaints;
        this.pendingComplaints = pendingComplaints;
        this.inProgressComplaints = inProgressComplaints;
        this.resolvedComplaints = resolvedComplaints;
        this.escalatedComplaints = escalatedComplaints;
        this.categoryDistribution = categoryDistribution;
        this.statusDistribution = statusDistribution;
        this.wardDistribution = wardDistribution;
    }

    public long getTotalComplaints() { return totalComplaints; }
    public void setTotalComplaints(long totalComplaints) { this.totalComplaints = totalComplaints; }

    public long getPendingComplaints() { return pendingComplaints; }
    public void setPendingComplaints(long pendingComplaints) { this.pendingComplaints = pendingComplaints; }

    public long getInProgressComplaints() { return inProgressComplaints; }
    public void setInProgressComplaints(long inProgressComplaints) { this.inProgressComplaints = inProgressComplaints; }

    public long getResolvedComplaints() { return resolvedComplaints; }
    public void setResolvedComplaints(long resolvedComplaints) { this.resolvedComplaints = resolvedComplaints; }

    public long getEscalatedComplaints() { return escalatedComplaints; }
    public void setEscalatedComplaints(long escalatedComplaints) { this.escalatedComplaints = escalatedComplaints; }

    public Map<String, Long> getCategoryDistribution() { return categoryDistribution; }
    public void setCategoryDistribution(Map<String, Long> categoryDistribution) { this.categoryDistribution = categoryDistribution; }

    public Map<String, Long> getStatusDistribution() { return statusDistribution; }
    public void setStatusDistribution(Map<String, Long> statusDistribution) { this.statusDistribution = statusDistribution; }

    public Map<String, Long> getWardDistribution() { return wardDistribution; }
    public void setWardDistribution(Map<String, Long> wardDistribution) { this.wardDistribution = wardDistribution; }

    public static AnalyticsSummaryDtoBuilder builder() { return new AnalyticsSummaryDtoBuilder(); }

    public static class AnalyticsSummaryDtoBuilder {
        private long totalComplaints;
        private long pendingComplaints;
        private long inProgressComplaints;
        private long resolvedComplaints;
        private long escalatedComplaints;
        private Map<String, Long> categoryDistribution;
        private Map<String, Long> statusDistribution;
        private Map<String, Long> wardDistribution;

        public AnalyticsSummaryDtoBuilder totalComplaints(long totalComplaints) { this.totalComplaints = totalComplaints; return this; }
        public AnalyticsSummaryDtoBuilder pendingComplaints(long pendingComplaints) { this.pendingComplaints = pendingComplaints; return this; }
        public AnalyticsSummaryDtoBuilder inProgressComplaints(long inProgressComplaints) { this.inProgressComplaints = inProgressComplaints; return this; }
        public AnalyticsSummaryDtoBuilder resolvedComplaints(long resolvedComplaints) { this.resolvedComplaints = resolvedComplaints; return this; }
        public AnalyticsSummaryDtoBuilder escalatedComplaints(long escalatedComplaints) { this.escalatedComplaints = escalatedComplaints; return this; }
        public AnalyticsSummaryDtoBuilder categoryDistribution(Map<String, Long> categoryDistribution) { this.categoryDistribution = categoryDistribution; return this; }
        public AnalyticsSummaryDtoBuilder statusDistribution(Map<String, Long> statusDistribution) { this.statusDistribution = statusDistribution; return this; }
        public AnalyticsSummaryDtoBuilder wardDistribution(Map<String, Long> wardDistribution) { this.wardDistribution = wardDistribution; return this; }

        public AnalyticsSummaryDto build() {
            return new AnalyticsSummaryDto(totalComplaints, pendingComplaints, inProgressComplaints, resolvedComplaints, escalatedComplaints, categoryDistribution, statusDistribution, wardDistribution);
        }
    }
}
