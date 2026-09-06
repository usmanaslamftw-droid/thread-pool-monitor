package com.usmanaslam.threadpoolmonitor.dto;

public record VirtualThreadBenchmarkResult(
        int taskCount,
        long platformThreadTimeMs,
        long virtualThreadTimeMs,
        double speedupFactor,
        String taskType
) {}
