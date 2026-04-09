package com.example.tp.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Alert")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "sessionId")
    private int sessionId;

    @Column(name = "type", length = 20, nullable = false)
    private String type;

    @Column(name = "message", length = 200)
    private String message;

    @Column(name = "emittedAt", nullable = false)
    private Date emittedAt;

    @Column(name = "read", nullable = false)
    private boolean read;

    public Alert() {
    }

    public Alert(int id, int userId, int sessionId, String type, String message, Date emittedAt, boolean read) {
        this.id = id;
        this.userId = userId;
        this.sessionId = sessionId;
        this.type = type;
        this.message = message;
        this.emittedAt = emittedAt;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getEmittedAt() {
        return emittedAt;
    }

    public void setEmittedAt(Date emittedAt) {
        this.emittedAt = emittedAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
