package com.example.tp.entities;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "SpecialistAppointment")
public class SpecialistAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "specialistId", nullable = false)
    private int specialistId;

    @Column(name = "dateTime", nullable = false)
    private Date dateTime;

    @Column(name = "status", length = 20, nullable = false)
    private String status;


    public SpecialistAppointment() {
    }

    public SpecialistAppointment(int id, int userId, int specialistId, Date dateTime, String status) {
        this.id = id;
        this.userId = userId;
        this.specialistId = specialistId;
        this.dateTime = dateTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getSpecialistId() {
        return specialistId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSpecialistId(int specialistId) {
        this.specialistId = specialistId;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
