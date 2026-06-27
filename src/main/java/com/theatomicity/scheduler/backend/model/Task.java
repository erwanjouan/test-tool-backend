package com.theatomicity.scheduler.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
public class Task implements Comparable<Task>, LifeCycleHooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "execution_id")
    private Execution execution;
    @ManyToOne
    @JoinColumn(name = "task_template_id", referencedColumnName = "id")
    private TaskTemplate taskTemplate;
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Param> params;
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private SortedSet<TaskLog> taskLogs = new TreeSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public Execution getExecution() { return execution; }
    public void setExecution(Execution execution) { this.execution = execution; }
    public TaskTemplate getTaskTemplate() { return taskTemplate; }
    public void setTaskTemplate(TaskTemplate taskTemplate) { this.taskTemplate = taskTemplate; }
    public List<Param> getParams() { return params; }
    public void setParams(List<Param> params) { this.params = params; }
    public SortedSet<TaskLog> getTaskLogs() { return taskLogs; }
    public void setTaskLogs(SortedSet<TaskLog> taskLogs) { this.taskLogs = taskLogs; }

    @Override
    public int compareTo(final Task o) {
        return this.getSortKey(this)
                .compareTo(this.getSortKey(o));
    }

    private String getSortKey(final Task task) {
        return Optional.ofNullable(task.getTaskTemplate())
                .map(TaskTemplate::getId)
                .orElse("");
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + this.id +
                ", status=" + this.status +
                ", startTime=" + this.startTime +
                ", endTime=" + this.endTime +
                ", taskTemplate=" + this.taskTemplate +
                ", params=" + this.params +
                '}';
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
}