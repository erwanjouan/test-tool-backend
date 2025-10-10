package com.theatomicity.scheduler.backend.web;

import com.theatomicity.scheduler.backend.service.ServerSentEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/sse")
@Slf4j
public class ServerSentEventController {

    @Autowired
    private ServerSentEventService serverSentEventService;

    @GetMapping("/subscribe/{subscriberId}")
    public SseEmitter streamSee(@PathVariable final String subscriberId) {
        return this.serverSentEventService.addEmitter(subscriberId);
    }
}