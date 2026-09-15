package org.example.datasource.repository;

import org.example.datasource.entity.TaskEntity;
import org.example.domain.enums.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByDifficulty(DifficultyLevel difficulty);
}
