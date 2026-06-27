package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.exception.SchedulerException;
import com.theatomicity.scheduler.backend.model.*;
import com.theatomicity.scheduler.backend.repository.ExecutionRepository;
import com.theatomicity.scheduler.backend.repository.TaskLogRepository;
import com.theatomicity.scheduler.backend.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.SortedSet;

@Component
public class CustomTaskScheduler {

    private static final Logger log = LoggerFactory.getLogger(CustomTaskScheduler.class);

    private final NotifyService notifyService;
    private final TaskRepository taskRepository;
    private final TaskLogRepository taskLogRepository;
    private final ExecutionRepository executionRepository;

    public CustomTaskScheduler(NotifyService notifyService,
                               TaskRepository taskRepository,
                               TaskLogRepository taskLogRepository,
                               ExecutionRepository executionRepository) {
        this.notifyService = notifyService;
        this.taskRepository = taskRepository;
        this.taskLogRepository = taskLogRepository;
        this.executionRepository = executionRepository;
    }

    public synchronized void onTaskEnd(final Task aTask) {
        this.taskRepository.findById(aTask.getId())
                .ifPresent(
                        task -> {
                            log.info("onTaskEnd {} {}", task.hashCode(), task);
                            if (Status.RUNNING.equals(task.getStatus())) {
                                task.onCompletionOk();
                            } else if (Status.CANCELLED.equals(task.getStatus())) {
                                task.onCancel();
                            } else if (Status.ERROR.equals(task.getStatus())) {
                                task.onCompletionError();
                            } else {
                                throw new SchedulerException("Unknown task status: " + task.getStatus());
                            }
                            final Task savedTask = this.taskRepository.save(task);
                            if (!Status.CANCELLED.equals(savedTask.getStatus())) {
                                this.notifyService.pushTaskStatus(savedTask);
                            }
                            final boolean shouldUpdateExecutionStatus = this.shouldUpdateExecutionStatus(savedTask);
                            final Execution execution = savedTask.getExecution();
                            if (shouldUpdateExecutionStatus) {
                                this.updateExecutionStatus(execution);
                            }
                        }
                );
    }

    private void updateExecutionStatus(final Execution execution) {
        final SortedSet<Task> tasks = execution.getTasks();
        final boolean allOk = tasks.stream().allMatch(task -> Status.COMPLETED.equals(task.getStatus()));
        final boolean someCancel = tasks.stream().anyMatch(task -> Status.CANCELLED.equals(task.getStatus()));
        final boolean someError = tasks.stream().anyMatch(task -> Status.ERROR.equals(task.getStatus()));
        if (allOk) {
            execution.onCompletionOk();
        } else if (someCancel) {
            execution.onCancel();
        } else if (someError) {
            execution.onCompletionError();
        }
        this.executionRepository.save(execution);
        this.notifyService.pushExecution(execution, SseEventType.EXECUTION_UPDATED);
    }

    private boolean shouldUpdateExecutionStatus(final Task savedTask) {
        final Execution execution = savedTask.getExecution();
        final SortedSet<Task> tasks = execution.getTasks();
        final Optional<Task> anyPending = tasks.stream()
                .filter(this::isNotFinished)
                .findAny();
        final boolean isCancellation = Status.CANCELLED.equals(savedTask.getStatus());
        final boolean noRemainingTask = anyPending.isEmpty();
        return isCancellation || noRemainingTask;
    }

    private boolean isNotFinished(final Task task) {
        return Status.RUNNING.equals(task.getStatus())
                || Status.CREATED.equals(task.getStatus());
    }

    public synchronized void startTask(final Task task) {
        task.onStart();
        this.taskRepository.save(task);
    }

    public synchronized void whenError(final Task task, final Exception exception) {
        task.onCompletionError();
        this.notifyService.logError(task, exception);
    }

    public synchronized void whenCancel(final Task task) {
        final String message = String.format("Task %d cancelled", task.getId());
        this.notifyService.logInfo(task, message);
    }

    public void logInfo(final Task task, final String message) {
        final TaskLog taskLog = new TaskLog();
        taskLog.setLogLevel(Level.INFO);
        taskLog.setMessage(message);
        final TaskLog saved = this.taskLogRepository.save(taskLog);
        task.getTaskLogs().add(saved);
    }
}
