package com.example.tp.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "Specialist")
public class Specialist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false, unique = true)
    private int userId;

    @Column(name = "specialty", length = 150, nullable = false)
    private String specialty;

    @Column(name = "modality", length = 60)
    private String modality;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    public Specialist() {
    }


    public Specialist(int id, int userId, String specialty, String modality, boolean verified) {
        this.id = id;
        this.userId = userId;
        this.specialty = specialty;
        this.modality = modality;
        this.verified = verified;
    }


    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getModality() {
        return modality;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
