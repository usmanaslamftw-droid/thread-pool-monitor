package com.usmanaslam.threadpoolmonitor.dto;

public record PoolInfo(
        String name,
        int coreSize,
        int maxSize,
        int queueCapacity,
        String description,
        String status
) {}
