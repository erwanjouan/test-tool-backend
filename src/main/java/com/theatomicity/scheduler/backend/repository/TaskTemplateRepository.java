package com.theatomicity.scheduler.backend.repository;

import com.theatomicity.scheduler.backend.model.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, String> {

    List<TaskTemplate> findByCategory(String category);
}
