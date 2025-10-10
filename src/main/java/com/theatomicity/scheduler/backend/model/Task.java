package com.theatomicity.scheduler.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// don't use @Data with Hibernate
// https://thorben-janssen.com/lombok-hibernate-how-to-avoid-common-pitfalls/

@Entity
@Getter
@Setter
public class Task implements Comparable<Task> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @JsonIgnore //to avoid circular references
    @ManyToOne
    @JoinColumn(name = "execution_id")
    private Execution execution;
    @ManyToOne
    @JoinColumn(name = "task_template_id", referencedColumnName = "id")
    private TaskTemplate taskTemplate;
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Param> params;

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
}
