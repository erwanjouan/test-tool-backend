package com.theatomicity.tnr.backend.service;

import com.theatomicity.tnr.backend.model.NrTest;
import com.theatomicity.tnr.backend.repository.NrTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NrTestService {

    @Autowired
    private NrTestRepository nrTestRepository;

    public List<NrTest> findAll() {
        return this.nrTestRepository.findAll();
    }

    public List<NrTest> findByPriorite(final Integer priorite) {
        return Objects.isNull(priorite) ?
                this.nrTestRepository.findAll()
                : this.nrTestRepository.findByPriorite(priorite);
    }

}
