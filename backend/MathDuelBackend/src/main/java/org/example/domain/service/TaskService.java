package org.example.datasource.service;

import org.example.domain.enums.DifficultyLevel;
import org.example.domain.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> getTasksByDifficulty(DifficultyLevel difficulty);

    Task getTaskById(Long id);
}
