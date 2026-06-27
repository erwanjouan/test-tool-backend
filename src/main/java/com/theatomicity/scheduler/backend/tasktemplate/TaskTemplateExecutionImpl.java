package com.theatomicity.scheduler.backend.tasktemplate;

import com.theatomicity.scheduler.backend.model.SchedulerCancelException;
import com.theatomicity.scheduler.backend.model.Status;
import com.theatomicity.scheduler.backend.model.Task;
import com.theatomicity.scheduler.backend.service.CustomTaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

@Component
public class TaskTemplateExecutionImpl implements TaskTemplateExecution {

    private static final Logger log = LoggerFactory.getLogger(TaskTemplateExecutionImpl.class);

    @Autowired
    private CustomTaskScheduler customTaskScheduler;

    @Autowired
    private ExecutorService executor;

    @Async
    @Override
    public Future<Task> process(final Task task) {
        if (Status.CANCELLED.equals(task.getStatus())) {
            this.customTaskScheduler.onTaskEnd(task);
        } else {
            this.customTaskScheduler.startTask(task);
            try {
                this.doProcess(task);
            } catch (final SchedulerCancelException schedulerCancelException) {
                log.info("SchedulerCancelException {}", schedulerCancelException.getMessage());
            } catch (final Exception exception) {
                this.customTaskScheduler.whenError(task, exception);
            } finally {
                this.customTaskScheduler.onTaskEnd(task);
            }
        }
        return CompletableFuture.completedFuture(task);
    }

    private void doProcess(final Task task) {
        final String content = task.getTaskTemplate().getContent();
        final Consumer<String> logger = message -> this.customTaskScheduler.logInfo(task, message);
        logger.accept(String.format("Performing %s", content));
        final ProcessBuilder builder = new ProcessBuilder();
        builder.command(content.split(" "));
        try {
            final Process process = builder.start();
            final StreamGobbler streamGobbler =
                    new StreamGobbler(process.getInputStream(), logger);
            final Future<?> future = this.executor.submit(streamGobbler);
            final int exitCode = process.waitFor();
            future.get();
            logger.accept(String.format("Done %s %d", content, exitCode));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        } catch (final ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
