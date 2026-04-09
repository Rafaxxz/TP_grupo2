package com.example.tp.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "GameSession")
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "gameName", length = 200, nullable = false)
    private String gameName;

    @Column(name = "gameCategory", length = 80)
    private String gameCategory;

    @Column(name = "gamePlatform", length = 60)
    private String gamePlatform;

    @Column(name = "startTime", nullable = false)
    private Date startTime;

    @Column(name = "endTime")
    private Date endTime;

    @Column(name = "durationMinutes", insertable = false, updatable = false)
    private Integer durationMinutes;

    @Column(name = "date", nullable = false)
    private Date date;

    public GameSession() {
    }

    public GameSession(int id, int userId, String gameName, String gameCategory, String gamePlatform, Date startTime, Date endTime, Integer durationMinutes, Date date) {
        this.id = id;
        this.userId = userId;
        this.gameName = gameName;
        this.gameCategory = gameCategory;
        this.gamePlatform = gamePlatform;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = durationMinutes;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameCategory() {
        return gameCategory;
    }

    public String getGamePlatform() {
        return gamePlatform;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setGameCategory(String gameCategory) {
        this.gameCategory = gameCategory;
    }

    public void setGamePlatform(String gamePlatform) {
        this.gamePlatform = gamePlatform;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
