package org.example.domain.model;

import org.example.domain.enums.DifficultyLevel;
import org.example.domain.enums.TaskType;

public class Task {

    private Long id;
    private String question;
    private String answer;
    private DifficultyLevel difficulty;
    private TaskType type;

    public Task() {
    }

    public Task(Long id, String question, String answer, DifficultyLevel difficulty, TaskType type) {
        this.id = id;
        this.question = question;
        this.answer = answer;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
