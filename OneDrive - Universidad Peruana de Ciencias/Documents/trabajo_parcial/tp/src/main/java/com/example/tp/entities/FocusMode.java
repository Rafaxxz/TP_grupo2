package com.example.tp.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "FocusMode")
public class FocusMode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "startTime", nullable = false)
    private Date startTime;

    @Column(name = "endTime")
    private Date endTime;

    @Column(name = "plannedDurationMin", nullable = false)
    private int plannedDurationMin;

    @Column(name = "blockedGames", length = 200, nullable = false)
    private String blockedGames;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    public FocusMode() {
    }

    public FocusMode(int id, int userId, Date startTime, Date endTime, int plannedDurationMin, String blockedGames, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.plannedDurationMin = plannedDurationMin;
        this.blockedGames = blockedGames;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getPlannedDurationMin() {
        return plannedDurationMin;
    }

    public String getBlockedGames() {
        return blockedGames;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setPlannedDurationMin(int plannedDurationMin) {
        this.plannedDurationMin = plannedDurationMin;
    }

    public void setBlockedGames(String blockedGames) {
        this.blockedGames = blockedGames;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
