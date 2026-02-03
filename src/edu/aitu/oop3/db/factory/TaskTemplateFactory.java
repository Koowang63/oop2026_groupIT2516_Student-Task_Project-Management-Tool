package edu.aitu.oop3.db.factory;

public class TaskTemplateFactory {
    public static TaskTemplate create(TaskTemplateType type) {
        return switch (type) {
            case SIMPLE -> new SimpleTaskTemplate();
            case DEADLINE_3_DAYS -> new DeadlineThreeDaysTemplate();
        };
    }
}