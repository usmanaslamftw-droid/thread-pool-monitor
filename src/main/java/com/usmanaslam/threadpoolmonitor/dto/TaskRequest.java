package com.usmanaslam.threadpoolmonitor.dto;

public record TaskRequest(
        String taskType,
        int durationMs,
        int count
) {}
