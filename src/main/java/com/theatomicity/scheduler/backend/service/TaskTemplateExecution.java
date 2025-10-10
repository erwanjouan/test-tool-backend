package com.theatomicity.scheduler.backend.service;

import com.theatomicity.scheduler.backend.model.Task;

import java.util.concurrent.Future;

public interface TaskTemplateExecution {

    Future<Task> process(Task task);

}