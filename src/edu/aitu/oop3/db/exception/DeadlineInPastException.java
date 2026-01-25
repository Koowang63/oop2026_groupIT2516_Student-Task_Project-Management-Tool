package edu.aitu.oop3.db.exception;

public class DeadlineInPastException extends RuntimeException {
    public DeadlineInPastException(String message) {
        super(message);
    }
}