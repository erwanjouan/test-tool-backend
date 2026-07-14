package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.model.Execution;
import com.theatomicity.scheduler.backend.model.SseEventType;
import com.theatomicity.scheduler.backend.model.Status;
import com.theatomicity.scheduler.backend.model.Task;
import com.theatomicity.scheduler.backend.repository.ExecutionRepository;
import com.theatomicity.scheduler.backend.tasktemplate.TaskTemplateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.theatomicity.scheduler.backend.model.Status.CREATED;
import static com.theatomicity.scheduler.backend.model.Status.RUNNING;

@Service
public class ExecutionService {

    private static final Logger log = LoggerFactory.getLogger(ExecutionService.class);

    private final ExecutionRepository executionRepository;
    private final CustomTaskScheduler customTaskScheduler;
    private final NotifyService notifyService;
    private final TaskTemplateExecution taskTemplateExecution;

    public ExecutionService(final ExecutionRepository executionRepository,
                            final CustomTaskScheduler customTaskScheduler,
                            final NotifyService notifyService,
                            final TaskTemplateExecution taskTemplateExecution) {
        this.executionRepository = executionRepository;
        this.customTaskScheduler = customTaskScheduler;
        this.notifyService = notifyService;
        this.taskTemplateExecution = taskTemplateExecution;
    }

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
                    this.doStart(execution);
                    return execution.getId();
                })
                .orElse(null);
    }

    private void doStart(final Execution execution) {
        execution.onStart();
        this.executionRepository.save(execution);
        execution.getTasks().forEach(task -> {
            task.onStart();
            this.taskTemplateExecution.process(task);
        });
        this.notifyService.pushExecution(execution, SseEventType.EXECUTION_UPDATED);
    }

    public Long delete(final Long executionId) {
        this.executionRepository.findById(executionId)
                .ifPresent(execution -> {
                    this.cancelPendingTasks(execution);
                    this.executionRepository.delete(execution);
                    this.notifyService.pushExecution(execution, SseEventType.EXECUTION_DELETED);
                });
        return executionId;
    }

    public Long duplicate(final Long executionId) {
        final List<Execution> duplicated = this.executionRepository.findAllById(executionId);
        if (duplicated.size() == 1) {
            final Execution cloned = duplicated.get(0);
            final Execution execution = new Execution();
            this.updateStatus(execution, CREATED);
            execution.setName(cloned.getName());
            execution.setDescription(cloned.getDescription());
            cloned.getTasks().forEach(task -> {
                final Task clonedTask = new Task();
                clonedTask.setTaskTemplate(task.getTaskTemplate());
                clonedTask.setExecution(execution);
                execution.getTasks().add(clonedTask);
            });
            final Execution saved = this.executionRepository.save(execution);
            this.notifyService.pushExecution(saved, SseEventType.EXECUTION_CREATED);
            return saved.getId();
        } else {
            throw new RuntimeException("Execution not found");
        }
    }

    private void updateStatus(final Execution execution, final Status status) {
        execution.setStatus(status);
        execution.getTasks().forEach(task -> task.setStatus(status));
    }

    public Long cancel(final Long executionId) {
        final Optional<Execution> execution = this.executionRepository.findById(executionId);
        execution.ifPresent(
                exec -> {
                    exec.onCancel();
                    this.cancelPendingTasks(exec);
                    final Execution saved = this.executionRepository.save(exec);
                    log.info("Execution {} has been cancelled", execution);
                    this.notifyService.pushExecution(saved, SseEventType.EXECUTION_UPDATED);
                }
        );
        return executionId;
    }

    private void cancelPendingTasks(final Execution exec) {
        exec.getTasks().forEach(
                task -> {
                    if (this.isNotCompleted(task.getStatus())) {
                        task.onCancel();
                        this.customTaskScheduler.whenCancel(task);
                    }
                }
        );
    }

    public Boolean isNotCompleted(final Status status) {
        return RUNNING.equals(status) || CREATED.equals(status);
    }
}
