package com.theatomicity.scheduler.backend.web;

import com.theatomicity.scheduler.backend.model.TaskTemplate;
import com.theatomicity.scheduler.backend.service.TaskTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskTemplateController {

    private final TaskTemplateService taskTemplateService;

    public TaskTemplateController(final TaskTemplateService taskTemplateService) {
        this.taskTemplateService = taskTemplateService;
    }

    @GetMapping("task-templates")
    public List<TaskTemplate> getTasks(@RequestParam(required = false) final String category) {
        return this.taskTemplateService.findByCategory(category);
    }
}