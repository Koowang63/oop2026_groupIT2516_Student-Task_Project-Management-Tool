package edu.aitu.oop3.db.db;

import edu.aitu.oop3.db.controller.AppController;
import edu.aitu.oop3.db.repository.*;
import edu.aitu.oop3.db.service.*;

public class Main {

    public static void main(String[] args) {

        IDatabase database = new SupabaseDatabase();

        UserRepository userRepository = new UserRepositoryJdbc(database);
        ProjectRepository projectRepository = new ProjectRepositoryJdbc(database);
        TaskRepository taskRepository = new TaskRepositoryJdbc(database);
        CommentRepository commentRepository = new CommentRepositoryJdbc(database);

        UserService userService = new UserService(userRepository);
        ProjectService projectService = new ProjectService(projectRepository);
        TaskService taskService = new TaskService(taskRepository, projectRepository);

        AppController controller = new AppController(
                userService,
                projectService,
                taskService
        );

        controller.run();
    }
}