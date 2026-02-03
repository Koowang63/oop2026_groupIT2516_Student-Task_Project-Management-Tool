package edu.aitu.oop3.db.factory;

import edu.aitu.oop3.db.builder.TaskBuilder;
import edu.aitu.oop3.db.entity.Task;
import edu.aitu.oop3.db.entity.TaskStatus;

public class SimpleTaskTemplate implements TaskTemplate {

    @Override
    public Task build(Long projectId, String title) {
        return new TaskBuilder()
                .projectId(projectId)
                .title(title)
                .status(TaskStatus.TODO)
                .deadline(null)
                .build();
    }
}