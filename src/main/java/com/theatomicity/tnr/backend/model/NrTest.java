package com.theatomicity.tnr.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

// don't use @Data with Hibernate
// https://thorben-janssen.com/lombok-hibernate-how-to-avoid-common-pitfalls/

@Entity
@Getter
@Setter
public class NrTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer priorite;
    private String reference;
    private String titre;
    private String description;
    private Boolean actif;
}
