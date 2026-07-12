package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.model.Execution;
import com.theatomicity.scheduler.backend.model.SseEventType;
import com.theatomicity.scheduler.backend.model.Task;
import com.theatomicity.scheduler.backend.model.TaskLog;
import com.theatomicity.scheduler.backend.repository.TaskLogRepository;
import com.theatomicity.scheduler.backend.sse.ServerSentEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

@Component
public class NotifyService {

    private static final Logger log = LoggerFactory.getLogger(NotifyService.class);

    private final ServerSentEventService serverSentEventService;
    private final TaskLogRepository taskLogRepository;

    public NotifyService(final ServerSentEventService serverSentEventService, final TaskLogRepository taskLogRepository) {
        this.serverSentEventService = serverSentEventService;
        this.taskLogRepository = taskLogRepository;
    }

    public void pushExecution(final Execution execution, final SseEventType sseEventType) {
        log.info("Pushing execution {} at {}", execution, sseEventType);
        this.serverSentEventService.sendExecution(execution, sseEventType);
    }

    public void pushTaskStatus(final Task task) {
        final Execution execution = task.getExecution();
        final String message = String.format("Task %s of execution %s is %s", task.getId(), execution.getId(), task.getStatus());
        final TaskLog taskLog = this.getTaskLog(task, Level.INFO, message);
        this.serverSentEventService.send(taskLog, SseEventType.LOG_PUBLISHED);
    }

    private TaskLog getTaskLog(final Task task, final Level level, final String message) {
        final TaskLog taskLog = new TaskLog();
        taskLog.setTask(task);
        taskLog.setEventDateTime(java.time.LocalDateTime.now());
        taskLog.setLogLevel(level);
        taskLog.setMessage(message);
        return taskLog;
    }

    public void logError(final Task task, final Exception exception) {
        final String message1 = exception.getMessage();
        final String message = message1.substring(0, Math.min(253, message1.length() - 1));
        final TaskLog taskLog = this.getTaskLog(task, Level.ERROR, message);
        final TaskLog saved = this.taskLogRepository.save(taskLog);
        this.serverSentEventService.send(saved, SseEventType.LOG_PUBLISHED);
    }

    public void logInfo(final Task task, final String message) {
        final TaskLog taskLog = this.getTaskLog(task, Level.INFO, message);
        final TaskLog saved = this.taskLogRepository.save(taskLog);
        log.info("Task.hashCode {} {}", task.hashCode(), message);
        this.serverSentEventService.send(saved, SseEventType.LOG_PUBLISHED);
    }
}
