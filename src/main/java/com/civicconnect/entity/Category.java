package com.civicconnect.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "default_sla_hours", nullable = false)
    private Integer defaultSlaHours;

    private String description;

    public Category() {}

    public Category(Long id, String name, Integer defaultSlaHours, String description) {
        this.id = id;
        this.name = name;
        this.defaultSlaHours = defaultSlaHours;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getDefaultSlaHours() { return defaultSlaHours; }
    public void setDefaultSlaHours(Integer defaultSlaHours) { this.defaultSlaHours = defaultSlaHours; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
