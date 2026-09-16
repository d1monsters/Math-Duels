package org.example.web.controller;

import org.example.domain.enums.DifficultyLevel;
import org.example.service.TaskService;
import org.example.web.dto.response.TaskResponse;
import org.example.web.mapper.TaskDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskDtoMapper taskDtoMapper;

    public TaskController(TaskService taskService, TaskDtoMapper taskDtoMapper) {
        this.taskService = taskService;
        this.taskDtoMapper = taskDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(defaultValue = "EASY") DifficultyLevel difficulty,
            @RequestParam(defaultValue = "3") int count
    ) {
        List<TaskResponse> tasks = taskService.getTasksByDifficulty(difficulty, count)
                .stream()
                .map(taskDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskDtoMapper.toResponse(taskService.getTaskById(id)));
    }
}
