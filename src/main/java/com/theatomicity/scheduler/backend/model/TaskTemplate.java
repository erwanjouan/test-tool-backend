package com.theatomicity.scheduler.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TaskTemplate {
    @Id
    private String id;
    private String title;
    private String content;
    private String category;
}
