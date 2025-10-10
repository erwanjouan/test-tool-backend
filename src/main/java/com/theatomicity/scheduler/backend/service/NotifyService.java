package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.model.Execution;
import com.theatomicity.scheduler.backend.model.SseEventType;
import org.springframework.stereotype.Component;

@Component
public class NotifyService {
    public void pushExecution(final Execution execution, final SseEventType sseEventType) {
        
    }
}
