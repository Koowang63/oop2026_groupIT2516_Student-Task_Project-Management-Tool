package edu.aitu.oop3.db.factory;

import edu.aitu.oop3.db.builder.TaskBuilder;
import edu.aitu.oop3.db.entity.Task;
import edu.aitu.oop3.db.entity.TaskStatus;

import java.time.LocalDateTime;

public class DeadlineThreeDaysTemplate implements TaskTemplate {
    @Override
    public Task build(Long projectId, String title) {
        return new TaskBuilder()
                .projectId(projectId)
                .title(title)
                .status(TaskStatus.TODO)
                .deadline(LocalDateTime.now().plusDays(3))
                .build();
    }
}