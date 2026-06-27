package com.theatomicity.scheduler.backend.model.dto;

import com.theatomicity.scheduler.backend.model.Param;
import com.theatomicity.scheduler.backend.model.Status;
import com.theatomicity.scheduler.backend.model.TaskTemplate;

import java.time.LocalDateTime;
import java.util.List;

public class TaskDto {
    private Long id;
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TaskTemplate taskTemplate;
    private List<Param> params;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public TaskTemplate getTaskTemplate() { return taskTemplate; }
    public void setTaskTemplate(TaskTemplate taskTemplate) { this.taskTemplate = taskTemplate; }
    public List<Param> getParams() { return params; }
    public void setParams(List<Param> params) { this.params = params; }
}
