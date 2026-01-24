package edu.aitu.oop3.db.entity;

import java.time.LocalDateTime;

public class Project {
    private Long id;
    private String title;
    private String description;
    private Long ownerId;          // user_id в БД
    private LocalDateTime createdAt;

    public Project() { }

    public Project(Long id, String title, String description, Long ownerId, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
    }

    public Project(String title, String description, Long ownerId) {
        this(null, title, description, ownerId, null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ownerId=" + ownerId +
                ", createdAt=" + createdAt +
                '}';
    }
}
