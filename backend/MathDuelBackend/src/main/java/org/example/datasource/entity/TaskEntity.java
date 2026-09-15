package org.example.datasource.entity;

import jakarta.persistence.*;
import org.example.domain.enums.DifficultyLevel;
import org.example.domain.enums.TaskType;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type;

    public TaskEntity() {
    }

    public TaskEntity(Long id, String question, String answer, DifficultyLevel difficulty, TaskType type) {
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
