package com.example.tp.entities;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "EducationalContent")
public class EducationalContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(name = "author", length = 150)
    private String author;

    @Column(name = "source", length = 200)
    private String source;

    @Column(name = "type", length = 25, nullable = false)
    private String type;

    @Column(name = "url", length = 200)
    private String url;

    @Column(name = "content", length = 200)
    private String content;

    @Column(name = "metadata", length = 200, nullable = false)
    private String metadata;

    @Column(name = "publishedAt")
    private Date publishedAt;

    public EducationalContent() {
    }

    public EducationalContent(int id, String title, String author, String source, String type, String url, String content, String metadata, Date publishedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.source = source;
        this.type = type;
        this.url = url;
        this.content = content;
        this.metadata = metadata;
        this.publishedAt = publishedAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public String getMetadata() {
        return metadata;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
}
