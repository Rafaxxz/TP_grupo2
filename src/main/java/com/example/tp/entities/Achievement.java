package com.example.tp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "iconUrl", length = 200)
    private String iconUrl;

    @Column(name = "pointsGranted", nullable = false)
    private int pointsGranted;

    @Column(name = "criteria", length = 30, nullable = false)
    private String criteria;

    @Column(name = "criteriaValue", nullable = false)
    private int criteriaValue;
    public Achievement() {
    }

    public Achievement(int id, String name, String description, String iconUrl, int pointsGranted, String criteria, int criteriaValue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.pointsGranted = pointsGranted;
        this.criteria = criteria;
        this.criteriaValue = criteriaValue;
    }

    public void setCriteriaValue(int criteriaValue) {
        this.criteriaValue = criteriaValue;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public void setPointsGranted(int pointsGranted) {
        this.pointsGranted = pointsGranted;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public int getPointsGranted() {
        return pointsGranted;
    }

    public String getCriteria() {
        return criteria;
    }

    public int getCriteriaValue() {
        return criteriaValue;
    }
}
