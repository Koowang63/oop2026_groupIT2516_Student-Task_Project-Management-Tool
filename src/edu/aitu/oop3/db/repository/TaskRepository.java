package edu.aitu.oop3.db.repository;

import edu.aitu.oop3.db.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findAll();

    void updateStatus(Long taskId, String status);
}