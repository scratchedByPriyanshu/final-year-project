package com.civicconnect.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "status_history")
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "updated_by_id", nullable = false)
    private User updatedBy;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Lob
    @Column(name = "photo_proof", columnDefinition = "LONGTEXT")
    private String photoProof;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public StatusHistory() {}

    public StatusHistory(Long id, Complaint complaint, String status, User updatedBy, String comments, String photoProof, LocalDateTime createdAt) {
        this.id = id;
        this.complaint = complaint;
        this.status = status;
        this.updatedBy = updatedBy;
        this.comments = comments;
        this.photoProof = photoProof;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Complaint getComplaint() { return complaint; }
    public void setComplaint(Complaint complaint) { this.complaint = complaint; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(User updatedBy) { this.updatedBy = updatedBy; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public String getPhotoProof() { return photoProof; }
    public void setPhotoProof(String photoProof) { this.photoProof = photoProof; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static StatusHistoryBuilder builder() { return new StatusHistoryBuilder(); }

    public static class StatusHistoryBuilder {
        private Long id;
        private Complaint complaint;
        private String status;
        private User updatedBy;
        private String comments;
        private String photoProof;
        private LocalDateTime createdAt;

        public StatusHistoryBuilder id(Long id) { this.id = id; return this; }
        public StatusHistoryBuilder complaint(Complaint complaint) { this.complaint = complaint; return this; }
        public StatusHistoryBuilder status(String status) { this.status = status; return this; }
        public StatusHistoryBuilder updatedBy(User updatedBy) { this.updatedBy = updatedBy; return this; }
        public StatusHistoryBuilder comments(String comments) { this.comments = comments; return this; }
        public StatusHistoryBuilder photoProof(String photoProof) { this.photoProof = photoProof; return this; }
        public StatusHistoryBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public StatusHistory build() {
            return new StatusHistory(id, complaint, status, updatedBy, comments, photoProof, createdAt);
        }
    }
}
