package com.theatomicity.scheduler.backend.web;

import com.theatomicity.scheduler.backend.model.Task;
import com.theatomicity.scheduler.backend.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("tasks")
    public List<Task> getTasks(@RequestParam(required = false) final String category) {
        return this.taskService.findByCategory(category);
    }
}