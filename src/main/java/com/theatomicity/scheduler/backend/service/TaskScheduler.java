package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.model.Execution;
import com.theatomicity.scheduler.backend.model.SseEventType;
import com.theatomicity.scheduler.backend.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskScheduler {

    private final NotifyService notifyService;
    private final ApplicationContext context;

    public void execute(final Execution execution) {
        execution.getTasks().forEach(this::execute);
        this.notifyService.pushExecution(execution, SseEventType.EXECUTION_UPDATED);
    }

    private void execute(final Task task) {
        final TaskTemplateExecution taskTemplateExecution = this.getTaskTemplateExecution(task);
        taskTemplateExecution.process(task);
    }

    private TaskTemplateExecution getTaskTemplateExecution(final Task task) {
        final String id = task.getTaskTemplate().getId();
        return this.context.getBean(id, TaskTemplateExecution.class);
    }

    public void onTaskEnd(final Task task) {

    }

    public void startTask(Task task) {

    }

    public void whenError(Task task, Exception exception) {
        
    }
}
