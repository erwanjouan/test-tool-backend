package com.theatomicity.scheduler.backend;

import com.theatomicity.scheduler.backend.model.Task;
import com.theatomicity.scheduler.backend.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("h2")
class TaskServiceTests {

    @Autowired
    private TaskService taskService;

    @Test
    void findAll() {
        final List<Task> tasks = this.taskService.findAll();
        assertEquals(1, tasks.size());
    }

    @Test
    void findByCategory() {
        final List<Task> tasks = this.taskService.findByCategory(null);
        assertEquals(1, tasks.size());
    }

}
