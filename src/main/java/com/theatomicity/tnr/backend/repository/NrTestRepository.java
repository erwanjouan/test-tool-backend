package com.theatomicity.tnr.backend.repository;

import com.theatomicity.tnr.backend.model.NrTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NrTestRepository extends JpaRepository<NrTest, Long> {

    List<NrTest> findByPriorite(Integer priorite);

}
