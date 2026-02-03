package edu.aitu.oop3.db.builder;

import edu.aitu.oop3.db.entity.Task;
import edu.aitu.oop3.db.entity.TaskStatus;

import java.time.LocalDateTime;

public class TaskBuilder {
    private Long id;
    private Long projectId;
    private String title;
    private TaskStatus status = TaskStatus.TODO; // default
    private LocalDateTime deadline;

    public TaskBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TaskBuilder projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public TaskBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TaskBuilder status(TaskStatus status) {
        this.status = status;
        return this;
    }

    public TaskBuilder deadline(LocalDateTime deadline) {
        this.deadline = deadline;
        return this;
    }

    public Task build() {
        return new Task(id, projectId, title, status, deadline);
    }
}