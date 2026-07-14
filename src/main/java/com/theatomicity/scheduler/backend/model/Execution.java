package com.theatomicity.scheduler.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
public class Execution implements LifeCycleHooks {
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

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(final LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(final LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public SortedSet<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(final SortedSet<Task> tasks) {
        this.tasks = tasks;
    }

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