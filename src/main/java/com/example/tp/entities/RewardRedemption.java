package com.example.tp.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "RewardRedemption")
public class RewardRedemption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "rewardId", nullable = false)
    private int rewardId;

    @Column(name = "pointsUsed", nullable = false)
    private int pointsUsed;

    @Column(name = "redeemedAt", nullable = false)
    private Date redeemedAt;

    public RewardRedemption() {
    }

    public RewardRedemption(int id, int userId, int rewardId, int pointsUsed, Date redeemedAt) {
        this.id = id;
        this.userId = userId;
        this.rewardId = rewardId;
        this.pointsUsed = pointsUsed;
        this.redeemedAt = redeemedAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getPointsUsed() {
        return pointsUsed;
    }

    public Date getRedeemedAt() {
        return redeemedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }

    public void setPointsUsed(int pointsUsed) {
        this.pointsUsed = pointsUsed;
    }

    public void setRedeemedAt(Date redeemedAt) {
        this.redeemedAt = redeemedAt;
    }
}
