package com.theatomicity.scheduler.backend;

import com.theatomicity.scheduler.backend.model.Execution;
import com.theatomicity.scheduler.backend.service.ExecutionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("h2")
class ExecutionServiceTests {

    @Autowired
    private ExecutionService executionServices;

    @Test
    void findAll() {
        final List<Execution> executionList = this.executionServices.findAll();
        assertEquals(12, executionList.size());
    }

}
