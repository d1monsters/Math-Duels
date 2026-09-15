package org.example.domain.exception;

public class TaskNotFoundException extends BusinessException {

    public TaskNotFoundException(Long taskId) {
        super("Task not found: " + taskId, "TASK_NOT_FOUND");
    }
}
