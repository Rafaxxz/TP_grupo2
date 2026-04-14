package com.example.tp.entities;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "SelfAssessmentTest")
public class SelfAssessmentTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "takenAt", nullable = false)
    private Date takenAt;

    @Column(name = "result", length = 10, nullable = false)
    private String result;

    @Column(name = "answers", length = 200, nullable = false)
    private String answers;

    public SelfAssessmentTest() {
    }

    public SelfAssessmentTest(int id, int userId, Date takenAt, String result, String answers) {
        this.id = id;
        this.userId = userId;
        this.takenAt = takenAt;
        this.result = result;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public String getResult() {
        return result;
    }

    public String getAnswers() {
        return answers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
