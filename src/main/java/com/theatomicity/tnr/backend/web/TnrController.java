package com.theatomicity.tnr.backend.web;

import com.theatomicity.tnr.backend.model.NrExecution;
import com.theatomicity.tnr.backend.model.NrTest;
import com.theatomicity.tnr.backend.service.NrExecutionService;
import com.theatomicity.tnr.backend.service.NrTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TnrController {

    @Autowired
    private NrTestService nrTestService;

    @Autowired
    private NrExecutionService nrExecutionService;

    @GetMapping("tests")
    @CrossOrigin(origins = "*")
    public List<NrTest> getNrTests(@RequestParam(required = false) final Integer priorite) {
        return this.nrTestService.findByPriorite(priorite);
    }

    @GetMapping("executions")
    @CrossOrigin(origins = "*")
    public List<NrExecution> getNrExecutions() {
        return this.nrExecutionService.findAll();
    }

    @PostMapping("executions/save")
    @CrossOrigin(origins = "*")
    public NrExecution saveNrExecution(@RequestBody final NrExecution nrExecution) {
        return this.nrExecutionService.save(nrExecution);
    }
}