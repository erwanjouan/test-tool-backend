package com.theatomicity.scheduler.backend.web;

import com.theatomicity.scheduler.backend.model.Execution;
import com.theatomicity.scheduler.backend.service.ExecutionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/executions")
public class ExecutionController {

    private final ExecutionService executionService;

    public ExecutionController(final ExecutionService executionService) {
        this.executionService = executionService;
    }

    @GetMapping
    public List<Execution> getExecutions() {
        return this.executionService.findAll();
    }

    @GetMapping("/{executionId}")
    public Execution get(@PathVariable final Long executionId) {
        return this.executionService.find(executionId);
    }

    @PostMapping("/duplicate")
    public Long duplicate(@RequestBody final Long executionId) {
        return this.executionService.duplicate(executionId);
    }

    @PostMapping("/save")
    public Execution save(@RequestBody final Execution execution) {
        return this.executionService.save(execution);
    }

    @PostMapping("/delete")
    public Long delete(@RequestBody final Long executionId) {
        return this.executionService.delete(executionId);
    }

    @PostMapping("/{executionId}/start")
    public Long start(@PathVariable final Long executionId) {
        return this.executionService.start(executionId);
    }

    @PostMapping("/cancel")
    public Long cancel(@RequestBody final Long executionId) {
        return this.executionService.cancel(executionId);
    }
}