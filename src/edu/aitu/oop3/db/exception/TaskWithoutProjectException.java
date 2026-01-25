package edu.aitu.oop3.db.exception;

public class TaskWithoutProjectException extends RuntimeException {
    public TaskWithoutProjectException(String message) {
        super(message);
    }
}