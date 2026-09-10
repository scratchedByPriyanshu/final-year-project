package com.civicconnect.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ComplaintDTOs {

    public static class ComplaintRequest {
        private String title;
        private String description;
        private Long categoryId;
        private Long wardId;
        private Double latitude;
        private Double longitude;
        private String address;
        private String photoUrl;

        public ComplaintRequest() {}
        public ComplaintRequest(String title, String description, Long categoryId, Long wardId, Double latitude, Double longitude, String address, String photoUrl) {
            this.title = title;
            this.description = description;
            this.categoryId = categoryId;
            this.wardId = wardId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.address = address;
            this.photoUrl = photoUrl;
        }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Long getCategoryId() { return categoryId; }
        public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

        public Long getWardId() { return wardId; }
        public void setWardId(Long wardId) { this.wardId = wardId; }

        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }

        public Double getLongitude() { return longitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getPhotoUrl() { return photoUrl; }
        public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    }

    public static class StatusUpdateRequest {
        private String status;
        private Long officerId;
        private Long departmentId;
        private String comments;
        private String photoProof;

        public StatusUpdateRequest() {}
        public StatusUpdateRequest(String status, Long officerId, Long departmentId, String comments, String photoProof) {
            this.status = status;
            this.officerId = officerId;
            this.departmentId = departmentId;
            this.comments = comments;
            this.photoProof = photoProof;
        }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Long getOfficerId() { return officerId; }
        public void setOfficerId(Long officerId) { this.officerId = officerId; }

        public Long getDepartmentId() { return departmentId; }
        public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

        public String getComments() { return comments; }
        public void setComments(String comments) { this.comments = comments; }

        public String getPhotoProof() { return photoProof; }
        public void setPhotoProof(String photoProof) { this.photoProof = photoProof; }
    }

    public static class ComplaintResponse {
        private Long id;
        private String complaintNumber;
        private String title;
        private String description;
        private String categoryName;
        private String departmentName;
        private String wardName;
        private Integer wardNumber;
        private String citizenName;
        private String officerName;
        private String status;
        private Double latitude;
        private Double longitude;
        private String address;
        private String photoUrl;
        private LocalDateTime slaDeadline;
        private Boolean isEscalated;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<TimelineItemDto> timeline;

        public ComplaintResponse() {}
        public ComplaintResponse(Long id, String complaintNumber, String title, String description, String categoryName, String departmentName, String wardName, Integer wardNumber, String citizenName, String officerName, String status, Double latitude, Double longitude, String address, String photoUrl, LocalDateTime slaDeadline, Boolean isEscalated, LocalDateTime createdAt, LocalDateTime updatedAt, List<TimelineItemDto> timeline) {
            this.id = id;
            this.complaintNumber = complaintNumber;
            this.title = title;
            this.description = description;
            this.categoryName = categoryName;
            this.departmentName = departmentName;
            this.wardName = wardName;
            this.wardNumber = wardNumber;
            this.citizenName = citizenName;
            this.officerName = officerName;
            this.status = status;
            this.latitude = latitude;
            this.longitude = longitude;
            this.address = address;
            this.photoUrl = photoUrl;
            this.slaDeadline = slaDeadline;
            this.isEscalated = isEscalated;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.timeline = timeline;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getComplaintNumber() { return complaintNumber; }
        public void setComplaintNumber(String complaintNumber) { this.complaintNumber = complaintNumber; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getCategoryName() { return categoryName; }
        public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

        public String getDepartmentName() { return departmentName; }
        public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

        public String getWardName() { return wardName; }
        public void setWardName(String wardName) { this.wardName = wardName; }

        public Integer getWardNumber() { return wardNumber; }
        public void setWardNumber(Integer wardNumber) { this.wardNumber = wardNumber; }

        public String getCitizenName() { return citizenName; }
        public void setCitizenName(String citizenName) { this.citizenName = citizenName; }

        public String getOfficerName() { return officerName; }
        public void setOfficerName(String officerName) { this.officerName = officerName; }

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

        public List<TimelineItemDto> getTimeline() { return timeline; }
        public void setTimeline(List<TimelineItemDto> timeline) { this.timeline = timeline; }

        public static ComplaintResponseBuilder builder() { return new ComplaintResponseBuilder(); }

        public static class ComplaintResponseBuilder {
            private Long id;
            private String complaintNumber;
            private String title;
            private String description;
            private String categoryName;
            private String departmentName;
            private String wardName;
            private Integer wardNumber;
            private String citizenName;
            private String officerName;
            private String status;
            private Double latitude;
            private Double longitude;
            private String address;
            private String photoUrl;
            private LocalDateTime slaDeadline;
            private Boolean isEscalated;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
            private List<TimelineItemDto> timeline;

            public ComplaintResponseBuilder id(Long id) { this.id = id; return this; }
            public ComplaintResponseBuilder complaintNumber(String complaintNumber) { this.complaintNumber = complaintNumber; return this; }
            public ComplaintResponseBuilder title(String title) { this.title = title; return this; }
            public ComplaintResponseBuilder description(String description) { this.description = description; return this; }
            public ComplaintResponseBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
            public ComplaintResponseBuilder departmentName(String departmentName) { this.departmentName = departmentName; return this; }
            public ComplaintResponseBuilder wardName(String wardName) { this.wardName = wardName; return this; }
            public ComplaintResponseBuilder wardNumber(Integer wardNumber) { this.wardNumber = wardNumber; return this; }
            public ComplaintResponseBuilder citizenName(String citizenName) { this.citizenName = citizenName; return this; }
            public ComplaintResponseBuilder officerName(String officerName) { this.officerName = officerName; return this; }
            public ComplaintResponseBuilder status(String status) { this.status = status; return this; }
            public ComplaintResponseBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
            public ComplaintResponseBuilder longitude(Double longitude) { this.longitude = longitude; return this; }
            public ComplaintResponseBuilder address(String address) { this.address = address; return this; }
            public ComplaintResponseBuilder photoUrl(String photoUrl) { this.photoUrl = photoUrl; return this; }
            public ComplaintResponseBuilder slaDeadline(LocalDateTime slaDeadline) { this.slaDeadline = slaDeadline; return this; }
            public ComplaintResponseBuilder isEscalated(Boolean isEscalated) { this.isEscalated = isEscalated; return this; }
            public ComplaintResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
            public ComplaintResponseBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
            public ComplaintResponseBuilder timeline(List<TimelineItemDto> timeline) { this.timeline = timeline; return this; }

            public ComplaintResponse build() {
                return new ComplaintResponse(id, complaintNumber, title, description, categoryName, departmentName, wardName, wardNumber, citizenName, officerName, status, latitude, longitude, address, photoUrl, slaDeadline, isEscalated, createdAt, updatedAt, timeline);
            }
        }
    }

    public static class TimelineItemDto {
        private Long id;
        private String status;
        private String updatedByName;
        private String comments;
        private String photoProof;
        private LocalDateTime createdAt;

        public TimelineItemDto() {}
        public TimelineItemDto(Long id, String status, String updatedByName, String comments, String photoProof, LocalDateTime createdAt) {
            this.id = id;
            this.status = status;
            this.updatedByName = updatedByName;
            this.comments = comments;
            this.photoProof = photoProof;
            this.createdAt = createdAt;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getUpdatedByName() { return updatedByName; }
        public void setUpdatedByName(String updatedByName) { this.updatedByName = updatedByName; }

        public String getComments() { return comments; }
        public void setComments(String comments) { this.comments = comments; }

        public String getPhotoProof() { return photoProof; }
        public void setPhotoProof(String photoProof) { this.photoProof = photoProof; }

        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

        public static TimelineItemDtoBuilder builder() { return new TimelineItemDtoBuilder(); }

        public static class TimelineItemDtoBuilder {
            private Long id;
            private String status;
            private String updatedByName;
            private String comments;
            private String photoProof;
            private LocalDateTime createdAt;

            public TimelineItemDtoBuilder id(Long id) { this.id = id; return this; }
            public TimelineItemDtoBuilder status(String status) { this.status = status; return this; }
            public TimelineItemDtoBuilder updatedByName(String updatedByName) { this.updatedByName = updatedByName; return this; }
            public TimelineItemDtoBuilder comments(String comments) { this.comments = comments; return this; }
            public TimelineItemDtoBuilder photoProof(String photoProof) { this.photoProof = photoProof; return this; }
            public TimelineItemDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

            public TimelineItemDto build() {
                return new TimelineItemDto(id, status, updatedByName, comments, photoProof, createdAt);
            }
        }
    }
}
