package com.example.tp.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "WellbeingLevel")
public class WellbeingLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "weekStart", nullable = false)
    private Date weekStart;

    @Column(name = "improvementPercentage")
    private Float improvementPercentage;

    public WellbeingLevel() {
    }

    public WellbeingLevel(int id, int userId, int level, Date weekStart, Float improvementPercentage) {
        this.id = id;
        this.userId = userId;
        this.level = level;
        this.weekStart = weekStart;
        this.improvementPercentage = improvementPercentage;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getLevel() {
        return level;
    }

    public Date getWeekStart() {
        return weekStart;
    }

    public Float getImprovementPercentage() {
        return improvementPercentage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setWeekStart(Date weekStart) {
        this.weekStart = weekStart;
    }

    public void setImprovementPercentage(Float improvementPercentage) {
        this.improvementPercentage = improvementPercentage;
    }
}
