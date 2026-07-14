package com.theatomicity.scheduler.backend.web;

import com.theatomicity.scheduler.backend.sse.ServerSentEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/sse")
public class ServerSentEventController {

    private final ServerSentEventService serverSentEventService;

    public ServerSentEventController(final ServerSentEventService serverSentEventService) {
        this.serverSentEventService = serverSentEventService;
    }

    @GetMapping("/subscribe/{subscriberId}")
    public SseEmitter streamSee(@PathVariable final String subscriberId) {
        return this.serverSentEventService.addEmitter(subscriberId);
    }
}
