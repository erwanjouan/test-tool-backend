package com.theatomicity.scheduler.backend.model.dto;

import com.theatomicity.scheduler.backend.model.Status;
import com.theatomicity.scheduler.backend.model.Task;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

@Getter
@Setter
public class ExecutionDto {
    private Long id;
    private String name;
    private String description;
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SortedSet<Task> tasks = new TreeSet<>();
}
