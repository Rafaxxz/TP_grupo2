package com.example.tp.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Column(name = "email", length = 254, nullable = false, unique = true)
    private String email;

    @Column(name = "passwordHash", length = 200, nullable = false)
    private String passwordHash;

    @Column(name = "role", length = 20, nullable = false)
    private String role;

    @Column(name = "parentId")
    private int parentId;

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "dailyLimitMin")
    private Integer dailyLimitMin;

    @Column(name = "weeklyLimitMin")
    private Integer weeklyLimitMin;

    @Column(name = "blockActive", nullable = false)
    private boolean blockActive;

    @Column(name = "totalPoints", nullable = false)
    private int totalPoints;

    @Column(name = "currentStreakDays", nullable = false)
    private int currentStreakDays;

    @Column(name = "bestStreakDays", nullable = false)
    private int bestStreakDays;

    @Column(name = "preferences", length = 200, nullable = false)
    private String preferences;

    @Column(name = "integrations", length = 200, nullable = false)
    private String integrations;

    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    public User() {
    }

    public User(int id, String name, String email, String passwordHash, String role, int parentId, Date birthDate, Integer dailyLimitMin, Integer weeklyLimitMin, boolean blockActive, int totalPoints, int currentStreakDays, int bestStreakDays, String preferences, String integrations, Date createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.parentId = parentId;
        this.birthDate = birthDate;
        this.dailyLimitMin = dailyLimitMin;
        this.weeklyLimitMin = weeklyLimitMin;
        this.blockActive = blockActive;
        this.totalPoints = totalPoints;
        this.currentStreakDays = currentStreakDays;
        this.bestStreakDays = bestStreakDays;
        this.preferences = preferences;
        this.integrations = integrations;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    public int getParentId() {
        return parentId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Integer getDailyLimitMin() {
        return dailyLimitMin;
    }

    public Integer getWeeklyLimitMin() {
        return weeklyLimitMin;
    }

    public boolean isBlockActive() {
        return blockActive;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getCurrentStreakDays() {
        return currentStreakDays;
    }

    public int getBestStreakDays() {
        return bestStreakDays;
    }

    public String getPreferences() {
        return preferences;
    }

    public String getIntegrations() {
        return integrations;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setDailyLimitMin(Integer dailyLimitMin) {
        this.dailyLimitMin = dailyLimitMin;
    }

    public void setWeeklyLimitMin(Integer weeklyLimitMin) {
        this.weeklyLimitMin = weeklyLimitMin;
    }

    public void setBlockActive(boolean blockActive) {
        this.blockActive = blockActive;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setCurrentStreakDays(int currentStreakDays) {
        this.currentStreakDays = currentStreakDays;
    }

    public void setBestStreakDays(int bestStreakDays) {
        this.bestStreakDays = bestStreakDays;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public void setIntegrations(String integrations) {
        this.integrations = integrations;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
