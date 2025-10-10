package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.model.Task;
import com.theatomicity.scheduler.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

    public List<Task> findByCategory(final String category) {
        return this.taskRepository.findAll();
    }

}
