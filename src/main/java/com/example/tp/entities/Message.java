package com.example.tp.entities;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "senderId", nullable = false)
    private int senderId;

    @Column(name = "recipientId", nullable = false)
    private int recipientId;

    @Column(name = "content", length = 200, nullable = false)
    private String content;

    @Column(name = "context", length = 20, nullable = false)
    private String context;

    @Column(name = "sentAt", nullable = false)
    private Date sentAt;

    @Column(name = "read", nullable = false)
    private boolean read;

    public Message() {
    }

    public Message(int id, int senderId, int recipientId, String content, String context, Date sentAt, boolean read) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.context = context;
        this.sentAt = sentAt;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public String getContent() {
        return content;
    }

    public String getContext() {
        return context;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
