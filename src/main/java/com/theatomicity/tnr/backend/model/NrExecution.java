package com.theatomicity.tnr.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// don't use @Data with Hibernate
// https://thorben-janssen.com/lombok-hibernate-how-to-avoid-common-pitfalls/

@Entity
@Getter
@Setter
public class NrExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String status;
    @OneToMany(mappedBy = "nrExecution", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<NrExecutionParam> nrExecutionParams;
}
