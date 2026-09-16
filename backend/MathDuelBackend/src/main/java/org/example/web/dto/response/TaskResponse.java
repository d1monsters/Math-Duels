package org.example.web.dto.response;

import org.example.domain.enums.DifficultyLevel;
import org.example.domain.enums.TaskType;

public class TaskResponse {

    private Long id;
    private String question;
    private DifficultyLevel difficulty;
    private TaskType type;

    public TaskResponse() {
    }

    public TaskResponse(Long id, String question, DifficultyLevel difficulty, TaskType type) {
        this.id = id;
        this.question = question;
        this.difficulty = difficulty;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }
}
