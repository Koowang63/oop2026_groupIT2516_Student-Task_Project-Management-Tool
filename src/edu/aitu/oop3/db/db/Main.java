package edu.aitu.oop3.db.db;

import edu.aitu.oop3.db.controller.AppController;
import edu.aitu.oop3.db.db.IDatabase;
import edu.aitu.oop3.db.db.SupabaseDatabase;
import edu.aitu.oop3.db.repository.CommentRepository;
import edu.aitu.oop3.db.repository.CommentRepositoryJdbc;
import edu.aitu.oop3.db.repository.ProjectRepository;
import edu.aitu.oop3.db.repository.ProjectRepositoryJdbc;
import edu.aitu.oop3.db.repository.TaskRepository;
import edu.aitu.oop3.db.repository.TaskRepositoryJdbc;
import edu.aitu.oop3.db.repository.UserRepository;
import edu.aitu.oop3.db.repository.UserRepositoryJdbc;
import edu.aitu.oop3.db.service.ProjectService;
import edu.aitu.oop3.db.service.TaskService;
import edu.aitu.oop3.db.service.UserService;

public class Main {
    public static void main(String[] args) {
        IDatabase database = new SupabaseDatabase();

        UserRepository userRepo = new UserRepositoryJdbc(database);
        ProjectRepository projectRepo = new ProjectRepositoryJdbc(database);
        TaskRepository taskRepo = new TaskRepositoryJdbc(database);
        CommentRepository commentRepo = new CommentRepositoryJdbc(database);

        UserService userService = new UserService(userRepo);
        ProjectService projectService = new ProjectService(projectRepo);
        TaskService taskService = new TaskService(taskRepo, projectRepo);

        AppController app = new AppController(userService, projectService, taskService);
        app.run();
    }
}