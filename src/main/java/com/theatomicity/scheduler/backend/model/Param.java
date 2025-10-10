package com.theatomicity.scheduler.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// don't use @Data with Hibernate
// https://thorben-janssen.com/lombok-hibernate-how-to-avoid-common-pitfalls/

@Entity
@Getter
@Setter
public class Param {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore //to avoid circular references
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    private String content;
}
