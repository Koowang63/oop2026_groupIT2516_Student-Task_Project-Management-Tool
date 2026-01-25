package edu.aitu.oop3.db.controller;

import edu.aitu.oop3.db.entity.TaskStatus;
import edu.aitu.oop3.db.service.ProjectService;
import edu.aitu.oop3.db.service.TaskService;
import edu.aitu.oop3.db.service.UserService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class AppController {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;

    public AppController(UserService userService, ProjectService projectService, TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1) Create user");
            System.out.println("2) Create project");
            System.out.println("3) Add task");
            System.out.println("4) Change task status");
            System.out.println("5) Exit");
            System.out.print("> ");

            String choice = sc.nextLine();

            try {
                if ("1".equals(choice)) {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.println(userService.createUser(name, email));
                } else if ("2".equals(choice)) {
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("OwnerId: ");
                    Long ownerId = Long.parseLong(sc.nextLine());
                    System.out.println(projectService.createProject(title, desc, ownerId));
                } else if ("3".equals(choice)) {
                    System.out.print("ProjectId: ");
                    Long projectId = Long.parseLong(sc.nextLine());
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Status (TODO/IN_PROGRESS/DONE/CANCELED): ");
                    TaskStatus status = TaskStatus.valueOf(sc.nextLine().trim());
                    System.out.print("Deadline (yyyy-MM-ddTHH:mm) or empty: ");
                    String deadlineStr = sc.nextLine().trim();
                    LocalDateTime deadline = deadlineStr.isEmpty() ? null : LocalDateTime.parse(deadlineStr);
                    System.out.println(taskService.addTask(projectId, title, status, deadline));
                } else if ("4".equals(choice)) {
                    System.out.print("TaskId: ");
                    Long taskId = Long.parseLong(sc.nextLine());
                    System.out.print("New status (TODO/IN_PROGRESS/DONE/CANCELED): ");
                    TaskStatus newStatus = TaskStatus.valueOf(sc.nextLine().trim());
                    taskService.changeStatus(taskId, newStatus);
                    System.out.println("OK");
                } else if ("5".equals(choice)) {
                    return;
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}