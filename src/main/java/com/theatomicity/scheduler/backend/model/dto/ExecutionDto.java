package com.theatomicity.scheduler.backend.model.dto;

import com.theatomicity.scheduler.backend.model.Status;
import com.theatomicity.scheduler.backend.model.Task;

import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

public class ExecutionDto {
    private Long id;
    private String name;
    private String description;
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SortedSet<Task> tasks = new TreeSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public SortedSet<Task> getTasks() { return tasks; }
    public void setTasks(SortedSet<Task> tasks) { this.tasks = tasks; }
}