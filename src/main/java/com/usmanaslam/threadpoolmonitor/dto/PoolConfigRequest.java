package com.usmanaslam.threadpoolmonitor.dto;

public record PoolConfigRequest(
        String name,
        int coreSize,
        int maxSize,
        int queueCapacity,
        String description
) {}
