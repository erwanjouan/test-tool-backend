package com.theatomicity.scheduler.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

// don't use @Data with Hibernate
// https://thorben-janssen.com/lombok-hibernate-how-to-avoid-common-pitfalls/

@Entity
@Getter
@Setter
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
    public void onError() {
        this.status = Status.ERROR;
    }

    @Override
    public void onCompletionOk() {
        this.status = Status.COMPLETED;
        this.endTime = LocalDateTime.now();
    }

    @Override
    public void onCompletionKo() {
        this.status = Status.ERROR;
        this.endTime = LocalDateTime.now();
    }

    @Override
    public void onCancel() {
        this.status = Status.CANCELLED;
        this.endTime = LocalDateTime.now();
    }
}
