package com.usmanaslam.threadpoolmonitor.controller;

import com.usmanaslam.threadpoolmonitor.dto.TaskRequest;
import com.usmanaslam.threadpoolmonitor.dto.TaskResponse;
import com.usmanaslam.threadpoolmonitor.service.TaskExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pools/{name}/tasks")
@Tag(name = "Task Execution")
public class TaskController {

    private final TaskExecutionService taskExecutionService;

    public TaskController(TaskExecutionService taskExecutionService) {
        this.taskExecutionService = taskExecutionService;
    }

    @PostMapping
    @Operation(summary = "Submit tasks to a pool")
    public List<TaskResponse> submitTasks(@PathVariable String name, @RequestBody TaskRequest request) {
        return taskExecutionService.submitTasks(name, request);
    }

    @PostMapping("/batch")
    @Operation(summary = "Submit a batch of tasks to a pool")
    public List<TaskResponse> submitBatch(@PathVariable String name, @RequestBody List<TaskRequest> requests) {
        List<TaskResponse> allResponses = new ArrayList<>();
        for (TaskRequest req : requests) {
            allResponses.addAll(taskExecutionService.submitTasks(name, req));
        }
        return allResponses;
    }
}
