package com.example.tp.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "Reward")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Column(name = "type", length = 20, nullable = false)
    private String type;

    @Column(name = "pointsCost", nullable = false)
    private int pointsCost;

    @Column(name = "resourceUrl", length = 200)
    private String resourceUrl;

    public Reward() {
    }

    public Reward(int id, String name, String type, int pointsCost, String resourceUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.pointsCost = pointsCost;
        this.resourceUrl = resourceUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPointsCost() {
        return pointsCost;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPointsCost(int pointsCost) {
        this.pointsCost = pointsCost;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}
