package com.theatomicity.scheduler.backend.repository;

import com.theatomicity.scheduler.backend.model.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionRepository extends JpaRepository<Execution, Long> {

    List<Execution> findAllByOrderByIdDesc();

    List<Execution> findAllById(Long id);
}
