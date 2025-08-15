package com.theatomicity.tnr.backend.web;

import com.theatomicity.tnr.backend.model.NrExecution;
import com.theatomicity.tnr.backend.model.NrTest;
import com.theatomicity.tnr.backend.service.NrExecutionService;
import com.theatomicity.tnr.backend.service.NrTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TnrController {

    @Autowired
    private NrTestService nrTestService;

    @Autowired
    private NrExecutionService nrExecutionService;

    @GetMapping("tests")
    public List<NrTest> getNrTests(@RequestParam(required = false) final Integer priorite) {
        return this.nrTestService.findByPriorite(priorite);
    }

    @GetMapping("executions")
    public List<NrExecution> getNrExecutions() {
        return this.nrExecutionService.findAll();
    }

    @PostMapping("executions/save")
    public NrExecution saveNrExecution(@RequestBody final NrExecution nrExecution) {
        return this.nrExecutionService.save(nrExecution);
    }

    @GetMapping("sse")
    public SseEmitter sse() {
        final SseEmitter sseEmitter = new SseEmitter(600_000L);
        //create a single thread for sending messages asynchronously
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                for (int i = 0; i < 40; i++) {
                    this.randomDelay();
                    if (i % 5 == 0) {
                        sseEmitter.send(SseEmitter.event()
                                .name("refresh")
                                .data("refresh"));
                    } else {
                        sseEmitter.send(SseEmitter.event()
                                .name("log")
                                .data("This " + i + " time."));
                    }
                }
            } catch (final Exception e) {
                sseEmitter.completeWithError(e);
            } finally {
                sseEmitter.complete();
            }
        });
        executor.shutdown();
        return sseEmitter;
    }

    private void randomDelay() {
        try {
            Thread.sleep(2000);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}