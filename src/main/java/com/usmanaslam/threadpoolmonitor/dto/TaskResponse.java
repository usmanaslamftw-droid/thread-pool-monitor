package com.usmanaslam.threadpoolmonitor.dto;

public record TaskResponse(
        String taskId,
        String poolName,
        String taskType,
        String status,
        long executionTimeMs
) {}
