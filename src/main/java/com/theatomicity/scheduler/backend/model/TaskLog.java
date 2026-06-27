package com.theatomicity.scheduler.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.slf4j.event.Level;

import java.time.LocalDateTime;

@Entity
public class TaskLog implements Comparable<TaskLog> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private Task task;
    private LocalDateTime eventDateTime;
    private Level logLevel;
    private String message;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
    public LocalDateTime getEventDateTime() { return eventDateTime; }
    public void setEventDateTime(LocalDateTime eventDateTime) { this.eventDateTime = eventDateTime; }
    public Level getLogLevel() { return logLevel; }
    public void setLogLevel(Level logLevel) { this.logLevel = logLevel; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public int compareTo(final TaskLog o) {
        return this.id.compareTo(o.id);
    }
}