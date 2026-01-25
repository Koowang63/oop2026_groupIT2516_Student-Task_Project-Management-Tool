package edu.aitu.oop3.db.entity;

import java.time.LocalDateTime;

public class Task {
    private Long id;
    private Long projectId;
    private String title;
    private TaskStatus status;
    private LocalDateTime deadline;

    public Task() {}

    public Task(Long id, Long projectId, String title, TaskStatus status, LocalDateTime deadline) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.status = status;
        this.deadline = deadline;
    }

    public Task(Long projectId, String title, TaskStatus status, LocalDateTime deadline) {
        this(null, projectId, title, status, deadline);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", deadline=" + deadline +
                '}';
    }
}