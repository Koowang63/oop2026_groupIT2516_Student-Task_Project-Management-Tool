package edu.aitu.oop3.db.service;

import edu.aitu.oop3.db.entity.Project;
import edu.aitu.oop3.db.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(String title, String description, Long ownerId) {
        if (ownerId == null) {
            throw new IllegalArgumentException("ownerId cannot be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title cannot be empty");
        }
        return projectRepository.save(new Project(title, description, ownerId));
    }

    public Optional<Project> getProject(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
