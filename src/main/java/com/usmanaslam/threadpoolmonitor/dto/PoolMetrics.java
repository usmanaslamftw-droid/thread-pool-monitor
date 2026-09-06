package com.usmanaslam.threadpoolmonitor.dto;

import java.time.LocalDateTime;

public record PoolMetrics(
        String poolName,
        int corePoolSize,
        int maxPoolSize,
        int activeCount,
        int poolSize,
        long completedTaskCount,
        long taskCount,
        int queueSize,
        int queueRemainingCapacity,
        boolean isShutdown,
        boolean isTerminated,
        LocalDateTime timestamp
) {}
