package com.example.tp.entities;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "UserAchievement")
public class UserAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "achievementId", nullable = false)
    private int achievementId;

    @Column(name = "unlockedAt", nullable = false)
    private Date unlockedAt;

    public UserAchievement() {
    }

    public UserAchievement(int id, int userId, int achievementId, Date unlockedAt) {
        this.id = id;
        this.userId = userId;
        this.achievementId = achievementId;
        this.unlockedAt = unlockedAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public Date getUnlockedAt() {
        return unlockedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public void setUnlockedAt(Date unlockedAt) {
        this.unlockedAt = unlockedAt;
    }
}
