package org.example.domain.service;

import org.example.datasource.mapper.TaskMapper;
import org.example.datasource.repository.TaskRepository;
import org.example.domain.enums.DifficultyLevel;
import org.example.domain.exception.TaskNotFoundException;
import org.example.domain.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<Task> getTasksByDifficulty(DifficultyLevel difficulty) {
        return taskRepository.findByDifficulty(difficulty)
                .stream()
                .map(taskMapper::toDomain)
                .toList();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDomain)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
