package edu.aitu.oop3.db.controller;

import edu.aitu.oop3.db.entity.Task;
import edu.aitu.oop3.db.entity.TaskStatus;
import edu.aitu.oop3.db.factory.TaskTemplateType;
import edu.aitu.oop3.db.service.ProjectService;
import edu.aitu.oop3.db.service.TaskService;
import edu.aitu.oop3.db.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
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
            System.out.println("5) Add comment");
            System.out.println("6) Create task from template");
            System.out.println("7) List tasks by status sorted by deadline");
            System.out.println("8) Exit");
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
                } else if ("6".equals(choice)) {
                    System.out.print("Template (SIMPLE/DEADLINE_3_DAYS): ");
                    TaskTemplateType type = TaskTemplateType.valueOf(sc.nextLine().trim());
                    System.out.print("ProjectId: ");
                    Long projectId = Long.parseLong(sc.nextLine());
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.println(taskService.addTaskFromTemplate(type, projectId, title));
                } else if ("7".equals(choice)) {
                    System.out.print("Status (TODO/IN_PROGRESS/DONE/CANCELED): ");
                    TaskStatus status = TaskStatus.valueOf(sc.nextLine().trim());
                    List<Task> tasks = taskService.getTasksByStatusSortedByDeadline(status);
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks.");
                    } else {
                        tasks.forEach(System.out::println);
                    }
                } else if ("8".equals(choice)) {
                    return;
                } else {
                    System.out.println("Unknown option");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}