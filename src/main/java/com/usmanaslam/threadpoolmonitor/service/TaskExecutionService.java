package com.usmanaslam.threadpoolmonitor.service;

import com.usmanaslam.threadpoolmonitor.dto.TaskRequest;
import com.usmanaslam.threadpoolmonitor.dto.TaskResponse;
import com.usmanaslam.threadpoolmonitor.model.ManagedPool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskExecutionService {
    
    private final PoolManagerService poolManagerService;

    public TaskExecutionService(PoolManagerService poolManagerService) {
        this.poolManagerService = poolManagerService;
    }

    public List<TaskResponse> submitTasks(String poolName, TaskRequest request) {
        ManagedPool pool = poolManagerService.getPool(poolName);
        List<TaskResponse> responses = new ArrayList<>();

        for (int i = 0; i < request.count(); i++) {
            String taskId = UUID.randomUUID().toString();
            Runnable task = createTask(request.taskType(), request.durationMs());
            pool.submitTask(task);
            responses.add(new TaskResponse(taskId, poolName, request.taskType(), "SUBMITTED", 0L));
        }

        return responses;
    }

    private Runnable createTask(String taskType, int durationMs) {
        return () -> {
            try {
                if ("CPU_BOUND".equalsIgnoreCase(taskType)) {
                    long endTime = System.currentTimeMillis() + durationMs;
                    while (System.currentTimeMillis() < endTime) {
                        Math.sqrt(Math.random());
                    }
                } else if ("IO_BOUND".equalsIgnoreCase(taskType)) {
                    Thread.sleep(durationMs / 2);
                    Math.sqrt(Math.random());
                    Thread.sleep(durationMs / 2);
                } else {
                    Thread.sleep(durationMs);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }
}
