package edu.aitu.oop3.db.repository;

import edu.aitu.oop3.db.entity.Task;

public interface TaskRepository extends Repository<Task, Long> {
    void updateStatus(Long taskId, String status);
}