package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.model.TaskTemplate;
import com.theatomicity.scheduler.backend.repository.TaskTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTemplateService {

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;

    public List<TaskTemplate> findByCategory(final String category) {
        return this.taskTemplateRepository.findByCategory(category);
    }
}
