package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.mapper.ExecutionDtoMapper;
import com.theatomicity.scheduler.backend.mapper.TaskDtoMapper;
import com.theatomicity.scheduler.backend.model.Execution;
import com.theatomicity.scheduler.backend.model.SseEventType;
import com.theatomicity.scheduler.backend.model.TaskLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

@Service
@Slf4j
public class ServerSentEventService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Autowired
    private TaskDtoMapper taskDtoMapper;

    @Autowired
    private ExecutionDtoMapper executionDtoMapper;

    public SseEmitter addEmitter(final String subscriberId) {
        final SseEmitter emitter = new SseEmitter();
        emitter.onTimeout(() -> {
            emitter.complete();
            this.removeEmitter(subscriberId);
        });
        emitter.onCompletion(() -> this.removeEmitter(subscriberId));
        emitter.onError((e) -> {
            emitter.completeWithError(e);
            this.removeEmitter(subscriberId);
        });

        return null;
    }

    private void removeEmitter(final String subscriberId) {
        this.emitters.remove(subscriberId);
    }

    public void send(final TaskLog taskLog, final SseEventType sseEventType) {
        if (this.emitters.isEmpty()) {
            return;
        }
        for (final Map.Entry<String, SseEmitter> entry : this.emitters.entrySet()) {
            final Map<String, Object> taskLogDto = this.toTaskLogDto(taskLog);
            this.sendToSeeEmitter(sseEventType, entry, taskLogDto);
        }
    }

    public void sendExecution(final Execution execution, final SseEventType sseEventType) {
        if (this.emitters.isEmpty()) {
            return;
        }
        for (final Map.Entry<String, SseEmitter> entry : this.emitters.entrySet()) {
            final Map<String, Object> taskLogDto = this.toTaskLogDto(execution, sseEventType);
            this.sendToSeeEmitter(sseEventType, entry, taskLogDto);
        }
    }

    private Map<String, Object> toTaskLogDto(final Execution execution, final SseEventType sseEventType) {
        final Map<String, Object> dto = new HashMap<>();
        dto.put("eventDateTime", LocalDateTime.now());
        dto.put("logLevel", Level.INFO);
        dto.put("message", String.format("%s Execution %s", sseEventType.name(), execution.getId()));
        Optional.of(execution)
                .ifPresent(exec -> {
                    dto.put("execution", this.executionDtoMapper.toExecutionDto(exec));
                });
        return dto;
    }

    private Map<String, Object> toTaskLogDto(final TaskLog taskLog) {
        final Map<String, Object> dto = new HashMap<>();
        dto.put("eventDateTime", taskLog.getEventDateTime());
        dto.put("logLevel", taskLog.getLogLevel());
        dto.put("message", taskLog.getMessage());
        Optional.ofNullable(taskLog.getTask())
                .ifPresent(task -> {
                    dto.put("task", this.taskDtoMapper.toTaskDto(task));
                    dto.put("execution", this.executionDtoMapper.toExecutionDto(task.getExecution()));
                });
        return dto;
    }

    @Scheduled(fixedRate = 10000)
    public void sendHeartbeat() {
        for (final Map.Entry<String, SseEmitter> entry : this.emitters.entrySet()) {
            try {
                entry.getValue().send(SseEmitter.event()
                        .name(SseEventType.HEARTBEAT.name())
                        .id(UUID.randomUUID().toString())
                        .data("Heartbeat"));
            } catch (final Exception e) {
                log.error("Error sending SSE event", e);
                this.removeEmitter(entry.getKey());
            }
        }
    }
    
    private void sendToSeeEmitter(final SseEventType sseEventType, final Map.Entry<String, SseEmitter> entry, final Map<String, Object> taskLogDto) {
        try {
            entry.getValue().send(SseEmitter.event()
                    .name(sseEventType.name())
                    .id(UUID.randomUUID().toString())
                    .data(taskLogDto));
        } catch (final Exception e) {
            log.error("Error sending SSE event", e);
            this.removeEmitter(entry.getKey());
        }
    }

}
