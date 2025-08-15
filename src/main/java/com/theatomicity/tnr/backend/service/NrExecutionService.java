package com.theatomicity.tnr.backend.service;

import com.theatomicity.tnr.backend.model.NrExecution;
import com.theatomicity.tnr.backend.repository.NrExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NrExecutionService {

    @Autowired
    private NrExecutionRepository nrExecutionRepository;

    public List<NrExecution> findAll() {
        return this.nrExecutionRepository.findAll();
    }

    public NrExecution save(final NrExecution nrExecution) {
        // https://www.baeldung.com/hibernate-not-null-error#bd-saving-an-association-referencing-an-unsaved-instance
        nrExecution.getNrExecutionParams()
                .forEach(nrExecutionParam -> nrExecutionParam.setNrExecution(nrExecution));
        return this.nrExecutionRepository.save(nrExecution);
    }
}
