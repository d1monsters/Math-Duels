package org.example.web.controller;

import jakarta.validation.Valid;
import org.example.domain.model.Task;
import org.example.service.MlClient;
import org.example.service.TaskService;
import org.example.web.dto.request.CheckAnswerRequest;
import org.example.web.dto.response.CheckAnswerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final TaskService taskService;
    private final MlClient mlClient;

    public GameController(TaskService taskService, MlClient mlClient) {
        this.taskService = taskService;
        this.mlClient = mlClient;
    }

    @PostMapping("/check")
    public ResponseEntity<CheckAnswerResponse> checkAnswer(
            @Valid @RequestBody CheckAnswerRequest request
    ) {
        Task task = taskService.getTaskById(request.getTaskId());

        Map mlResponse = mlClient.validateAnswer(
                task.getQuestion(),
                task.getAnswer(),
                request.getAnswer()
        );

        boolean correct = false;
        if (mlResponse != null) {
            correct = (boolean) mlResponse.get("correct");
        }

        return ResponseEntity.ok(new CheckAnswerResponse(correct));
    }
}
