package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.model.Execution;
import com.theatomicity.scheduler.backend.model.Status;
import com.theatomicity.scheduler.backend.model.Task;
import com.theatomicity.scheduler.backend.repository.ExecutionRepository;
import com.theatomicity.scheduler.backend.repository.TaskTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExecutionService {

    private final ExecutionRepository executionRepository;

    private final TaskTemplateRepository taskTemplateRepository;

    private final TaskScheduler taskScheduler;

    public List<Execution> findAll() {
        return this.executionRepository.findAllByOrderByIdDesc();
    }

    public Execution save(final Execution execution) {
        if (Objects.isNull(execution.getId())) {
            execution.onCreate();
        }
        execution.getTasks()
                .forEach(task -> {
                    task.setExecution(execution);
                    task.getParams().forEach(param -> {
                        param.setTask(task);
                    });
                });
        return this.executionRepository.save(execution);
    }

    public Execution create(final Execution execution) {
        return this.executionRepository.save(execution);
    }

    public Execution find(final Long executionId) {
        return this.executionRepository.findById(executionId)
                .orElse(this.createNew());
    }

    private Execution createNew() {
        return new Execution();
    }

    public Long start(final Long executionId) {
        return this.executionRepository.findById(executionId)
                .map(execution -> {
                    execution.setStatus(Status.RUNNING);
                    execution.setStartTime(java.time.LocalDateTime.now());
                    final Execution saved = this.executionRepository.save(execution);
                    this.taskScheduler.execute(saved);
                    return saved.getId();
                })
                .orElse(null);
    }

    public Long delete(final Long executionId) {
        return null;
    }

    public Execution duplicate(final Long executionId) {
        return this.executionRepository.findById(executionId)
                .map(clonedExecution -> {
                            final Execution execution = new Execution();
                            execution.setStatus(Status.CREATED);
                            execution.setName(clonedExecution.getName());
                            execution.setDescription(clonedExecution.getDescription());
                            for (final Task clonedTask : clonedExecution.getTasks()) {
                                final Task task = new Task();
                                task.setTaskTemplate(clonedTask.getTaskTemplate());
                            }
                            return execution;
                        }
                ).orElse(null);
    }
}
