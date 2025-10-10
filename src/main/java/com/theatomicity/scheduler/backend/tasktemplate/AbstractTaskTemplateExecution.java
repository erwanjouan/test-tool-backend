package com.theatomicity.scheduler.backend.tasktemplate;

import com.theatomicity.scheduler.backend.model.SchedulerCancelException;
import com.theatomicity.scheduler.backend.model.Status;
import com.theatomicity.scheduler.backend.model.Task;
import com.theatomicity.scheduler.backend.service.TaskScheduler;
import com.theatomicity.scheduler.backend.service.TaskTemplateExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
public abstract class AbstractTaskTemplateExecution implements TaskTemplateExecution {

    @Autowired
    private TaskScheduler taskScheduler;

    @Override
    public Future<Task> process(final Task task) {
        if (Status.CANCELLED.equals(task.getStatus())) {
            this.taskScheduler.onTaskEnd(task);
        } else {
            this.taskScheduler.startTask(task);
            try {
                this.doProcess(task);
            } catch (final SchedulerCancelException schedulerCancelException) {
                log.info("SchedulerCancelException {}", schedulerCancelException.getMessage());
            } catch (final Exception exception) {
                this.taskScheduler.whenError(task, exception);
            } finally {
                this.taskScheduler.onTaskEnd(task);
            }
        }
        return CompletableFuture.completedFuture(task);
    }

    protected abstract void doProcess(Task task);
}
