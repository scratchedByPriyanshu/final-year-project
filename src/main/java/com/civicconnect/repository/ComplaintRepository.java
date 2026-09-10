package com.civicconnect.repository;

import com.civicconnect.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Optional<Complaint> findByComplaintNumber(String complaintNumber);
    List<Complaint> findByCitizenIdOrderByCreatedAtDesc(Long citizenId);
    List<Complaint> findByDepartmentId(Long departmentId);
    List<Complaint> findByOfficerId(Long officerId);
    List<Complaint> findByStatus(String status);
    List<Complaint> findByWardId(Long wardId);

    @Query("SELECT c.status, COUNT(c) FROM Complaint c GROUP BY c.status")
    List<Object[]> countComplaintsByStatus();

    @Query("SELECT c.category.name, COUNT(c) FROM Complaint c GROUP BY c.category.name")
    List<Object[]> countComplaintsByCategory();

    @Query("SELECT c.ward.wardName, COUNT(c) FROM Complaint c GROUP BY c.ward.wardName")
    List<Object[]> countComplaintsByWard();
}
