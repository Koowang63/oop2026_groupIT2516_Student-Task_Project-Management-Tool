package edu.aitu.oop3.db.service;

import edu.aitu.oop3.db.entity.Task;
import edu.aitu.oop3.db.entity.TaskStatus;
import edu.aitu.oop3.db.exception.DeadlineInPastException;
import edu.aitu.oop3.db.exception.InvalidStatusTransitionException;
import edu.aitu.oop3.db.exception.TaskWithoutProjectException;
import edu.aitu.oop3.db.factory.TaskTemplate;
import edu.aitu.oop3.db.factory.TaskTemplateFactory;
import edu.aitu.oop3.db.factory.TaskTemplateType;
import edu.aitu.oop3.db.repository.ProjectRepository;
import edu.aitu.oop3.db.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public Task addTask(Long projectId, String title, TaskStatus status, LocalDateTime deadline) {
        if (projectId == null) {
            throw new TaskWithoutProjectException("Task must belong to a project");
        }

        if (projectRepository.findById(projectId).isEmpty()) {
            throw new TaskWithoutProjectException("Project not found: " + projectId);
        }

        if (deadline != null && deadline.isBefore(LocalDateTime.now())) {
            throw new DeadlineInPastException("Deadline cannot be in the past");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        TaskStatus safeStatus = (status == null) ? TaskStatus.TODO : status;

        return taskRepository.save(new Task(projectId, title, safeStatus, deadline));
    }

    public Optional<Task> getTask(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void changeStatus(Long taskId, TaskStatus newStatus) {
        if (newStatus == null) {
            throw new InvalidStatusTransitionException("New status cannot be null");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found: " + taskId));

        TaskStatus oldStatus = task.getStatus();
        if (oldStatus == null) {
            oldStatus = TaskStatus.TODO;
        }

        if (!isValidTransition(oldStatus, newStatus)) {
            throw new InvalidStatusTransitionException("Invalid transition: " + oldStatus + " -> " + newStatus);
        }

        taskRepository.updateStatus(taskId, newStatus.name());
    }

    private boolean isValidTransition(TaskStatus from, TaskStatus to) {
        if (from == null || to == null) return false;
        if (from == TaskStatus.CANCELED) return false;
        if (from == TaskStatus.DONE) return false;
        if (from == TaskStatus.TODO) return to == TaskStatus.IN_PROGRESS || to == TaskStatus.CANCELED;
        if (from == TaskStatus.IN_PROGRESS) return to == TaskStatus.DONE || to == TaskStatus.CANCELED;
        return false;
    }

    public List<Task> getTasksByStatusSortedByDeadline(TaskStatus status) {
        return taskRepository.findAll().stream()
                .filter(t -> t.getStatus() == status)
                .sorted(Comparator.comparing(Task::getDeadline,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    public Task addTaskFromTemplate(TaskTemplateType type, Long projectId, String title) {

        if (type == null) {
            throw new IllegalArgumentException("Template type cannot be null");
        }

        if (projectId == null) {
            throw new TaskWithoutProjectException("Task must belong to a project");
        }

        if (projectRepository.findById(projectId).isEmpty()) {
            throw new TaskWithoutProjectException("Project not found: " + projectId);
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        TaskTemplate template = TaskTemplateFactory.create(type);
        Task task = template.build(projectId, title);

        if (task.getDeadline() != null && task.getDeadline().isBefore(LocalDateTime.now())) {
            throw new DeadlineInPastException("Deadline cannot be in the past");
        }

        return taskRepository.save(task);
    }
}