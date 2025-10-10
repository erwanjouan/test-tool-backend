package com.theatomicity.scheduler.backend.model.dto;

import com.theatomicity.scheduler.backend.model.Param;
import com.theatomicity.scheduler.backend.model.Status;
import com.theatomicity.scheduler.backend.model.TaskTemplate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TaskDto {
    private Long id;
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TaskTemplate taskTemplate;
    private List<Param> params;
}
