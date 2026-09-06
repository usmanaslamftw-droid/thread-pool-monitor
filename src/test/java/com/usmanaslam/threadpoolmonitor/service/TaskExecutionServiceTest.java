package com.usmanaslam.threadpoolmonitor.service;

import com.usmanaslam.threadpoolmonitor.dto.PoolConfigRequest;
import com.usmanaslam.threadpoolmonitor.dto.TaskRequest;
import com.usmanaslam.threadpoolmonitor.dto.TaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskExecutionServiceTest {

    private TaskExecutionService taskService;
    private PoolManagerService poolService;

    @BeforeEach
    void setUp() {
        poolService = new PoolManagerService();
        taskService = new TaskExecutionService(poolService);
    }

    @Test
    void testSubmitTasks() {
        poolService.createPool(new PoolConfigRequest("testPool", 2, 4, 100, "test"));
        List<TaskResponse> responses = taskService.submitTasks("testPool", new TaskRequest("SLEEP", 100, 5));
        assertEquals(5, responses.size());
    }
}
