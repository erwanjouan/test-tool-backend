package com.theatomicity.scheduler.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.event.Level;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskLog implements Comparable<TaskLog> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    private LocalDateTime eventDateTime;
    private Level logLevel;
    private String message;

    @Override
    public int compareTo(final TaskLog o) {
        return this.id.compareTo(o.id);
    }
}
