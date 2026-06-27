package com.theatomicity.scheduler.backend.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
public class Execution implements LifeCycleHooks, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @OneToMany(mappedBy = "execution", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    @Override
    public void onCreate() {
        this.status = Status.CREATED;
    }

    @Override
    public void onStart() {
        this.status = Status.RUNNING;
        this.startTime = LocalDateTime.now();
    }

    @Override
    public void onCompletionError() {
        this.status = Status.ERROR;
        this.endTime = LocalDateTime.now();
    }

    @Override
    public void onCompletionOk() {
        this.status = Status.COMPLETED;
        this.endTime = LocalDateTime.now();
    }

    @Override
    public void onCancel() {
        this.status = Status.CANCELLED;
        this.endTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Execution{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                ", status=" + this.status +
                ", startTime=" + this.startTime +
                ", endTime=" + this.endTime +
                ", tasks=" + this.tasks +
                '}';
    }
}