package edu.aitu.oop3.db.factory;

import edu.aitu.oop3.db.entity.Task;

public interface TaskTemplate {
    Task build(Long projectId, String title);
}