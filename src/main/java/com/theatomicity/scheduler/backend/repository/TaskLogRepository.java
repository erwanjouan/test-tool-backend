package com.theatomicity.scheduler.backend.repository;

import com.theatomicity.scheduler.backend.model.TaskLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskLogRepository extends JpaRepository<TaskLog, Long> {
}
