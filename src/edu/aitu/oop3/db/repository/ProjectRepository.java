package edu.aitu.oop3.db.repository;

import edu.aitu.oop3.db.entity.Project;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project save(Project project);
    Optional<Project> findById(Long id);
    List<Project> findAll();
}