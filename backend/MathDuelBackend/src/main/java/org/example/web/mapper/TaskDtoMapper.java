package org.example.web.mapper;

import org.example.domain.model.Task;
import org.example.web.dto.response.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoMapper {

    public TaskResponse toResponse(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskResponse(
                task.getId(),
                task.getQuestion(),
                task.getDifficulty(),
                task.getType()
        );
    }
}
