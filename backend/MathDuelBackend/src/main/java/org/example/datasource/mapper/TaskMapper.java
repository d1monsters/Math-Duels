package org.example.datasource.mapper;

import org.example.datasource.entity.TaskEntity;
import org.example.domain.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toDomain(TaskEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Task(
                entity.getId(),
                entity.getQuestion(),
                entity.getAnswer(),
                entity.getDifficulty(),
                entity.getType()
        );
    }

    public TaskEntity toEntity(Task task) {
        if (task == null) {
            return null;
        }
        TaskEntity entity = new TaskEntity();
        entity.setId(task.getId());
        entity.setQuestion(task.getQuestion());
        entity.setAnswer(task.getAnswer());
        entity.setDifficulty(task.getDifficulty());
        entity.setType(task.getType());
        return entity;
    }
}
