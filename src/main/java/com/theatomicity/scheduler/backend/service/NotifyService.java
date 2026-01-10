package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.model.Execution;
import com.theatomicity.scheduler.backend.model.SseEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotifyService {

    private final ServerSentEventService serverSentEventService;

    public void pushExecution(final Execution execution, final SseEventType sseEventType) {
        this.serverSentEventService.sendExecution(execution, sseEventType);
    }
}
