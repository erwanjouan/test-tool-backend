package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.model.Task;
import com.theatomicity.scheduler.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

}
