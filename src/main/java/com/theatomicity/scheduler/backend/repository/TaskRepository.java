package com.theatomicity.scheduler.backend.repository;

import com.theatomicity.scheduler.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getTasksByIdIs(Long id);
}
