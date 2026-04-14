package com.example.tp.entities;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ClinicalDataAccess")
public class ClinicalDataAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "specialistId", nullable = false)
    private int specialistId;

    @Column(name = "consentActive", nullable = false)
    private boolean consentActive;

    @Column(name = "grantedAt", nullable = false)
    private Date grantedAt;

    public ClinicalDataAccess() {
    }

    public ClinicalDataAccess(int id, int userId, int specialistId, boolean consentActive, Date grantedAt) {
        this.id = id;
        this.userId = userId;
        this.specialistId = specialistId;
        this.consentActive = consentActive;
        this.grantedAt = grantedAt;
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

    public boolean isConsentActive() {
        return consentActive;
    }

    public Date getGrantedAt() {
        return grantedAt;
    }

    public void setGrantedAt(Date grantedAt) {
        this.grantedAt = grantedAt;
    }

    public void setConsentActive(boolean consentActive) {
        this.consentActive = consentActive;
    }

    public void setSpecialistId(int specialistId) {
        this.specialistId = specialistId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
