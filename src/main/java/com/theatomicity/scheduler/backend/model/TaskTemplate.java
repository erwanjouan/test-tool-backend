package com.theatomicity.scheduler.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TaskTemplate {
    @Id
    private String id;
    private String title;
    private String content;
    private String category;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}