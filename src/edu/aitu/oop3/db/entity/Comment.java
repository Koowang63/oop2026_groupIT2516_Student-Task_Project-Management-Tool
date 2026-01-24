package edu.aitu.oop3.db.entity;

import java.time.LocalDateTime;

public class Comment {
    private Long id;
    private Long taskId;
    private Long userId;
    private String text;
    private LocalDateTime createdAt;

    public Comment() { }

    public Comment(Long id, Long taskId, Long userId, String text, LocalDateTime createdAt) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Comment(Long taskId, Long userId, String text) {
        this(null, taskId, userId, text, null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
