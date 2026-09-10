package com.civicconnect.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wards")
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ward_number", nullable = false, unique = true)
    private Integer wardNumber;

    @Column(name = "ward_name", nullable = false)
    private String wardName;

    @Column(nullable = false)
    private String zone;

    public Ward() {}

    public Ward(Long id, Integer wardNumber, String wardName, String zone) {
        this.id = id;
        this.wardNumber = wardNumber;
        this.wardName = wardName;
        this.zone = zone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getWardNumber() { return wardNumber; }
    public void setWardNumber(Integer wardNumber) { this.wardNumber = wardNumber; }

    public String getWardName() { return wardName; }
    public void setWardName(String wardName) { this.wardName = wardName; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }
}
