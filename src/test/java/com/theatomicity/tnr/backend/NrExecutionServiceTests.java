package com.theatomicity.tnr.backend;

import com.theatomicity.tnr.backend.model.NrExecution;
import com.theatomicity.tnr.backend.model.NrExecutionParam;
import com.theatomicity.tnr.backend.service.NrExecutionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("h2")
class NrExecutionServiceTests {

    @Autowired
    private NrExecutionService nrExecutionServices;

    @Test
    void findAll() {
        final List<NrExecution> nrExecutionList = this.nrExecutionServices.findAll();
        assertEquals(2, nrExecutionList.size());
        final NrExecution nrExecution = nrExecutionList.get(0);
        final List<NrExecutionParam> executionParams = nrExecution.getNrExecutionParams();
        assertEquals(2, executionParams.size());
    }

}
