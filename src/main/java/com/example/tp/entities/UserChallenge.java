package com.example.tp.entities;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "UserChallenge")
public class UserChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "challengeId", nullable = false)
    private int challengeId;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "acceptedAt", nullable = false)
    private Date acceptedAt;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @Column(name = "finishedAt")
    private Date finishedAt;

}
