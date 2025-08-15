package com.theatomicity.tnr.backend;

import com.theatomicity.tnr.backend.model.NrTest;
import com.theatomicity.tnr.backend.service.NrTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("h2")
class NrTestServiceTests {

    @Autowired
    private NrTestService nrTestService;

    @Test
    void findAll() {
        final List<NrTest> nrTests = this.nrTestService.findAll();
        assertEquals(9, nrTests.size());
    }

    @Test
    void findByPriorite() {
        final Integer priorite = 1;
        final List<NrTest> nrTests = this.nrTestService.findByPriorite(priorite);
        assertEquals(3, nrTests.size());
    }

}
