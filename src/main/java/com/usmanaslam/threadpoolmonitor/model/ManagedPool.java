package com.usmanaslam.threadpoolmonitor.model;

import com.usmanaslam.threadpoolmonitor.dto.PoolMetrics;

import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ManagedPool {
    private final String name;
    private final String description;
    private final ThreadPoolExecutor executor;
    private final LocalDateTime createdAt;

    public ManagedPool(String name, int coreSize, int maxSize, int queueCapacity, String description) {
        this.name = name;
        this.description = description;
        this.executor = new ThreadPoolExecutor(
                coreSize,
                maxSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueCapacity)
        );
        this.createdAt = LocalDateTime.now();
    }

    public PoolMetrics getMetrics() {
        return new PoolMetrics(
                name,
                executor.getCorePoolSize(),
                executor.getMaximumPoolSize(),
                executor.getActiveCount(),
                executor.getPoolSize(),
                executor.getCompletedTaskCount(),
                executor.getTaskCount(),
                executor.getQueue().size(),
                executor.getQueue().remainingCapacity(),
                executor.isShutdown(),
                executor.isTerminated(),
                LocalDateTime.now()
        );
    }

    public void shutdown() {
        executor.shutdown();
    }

    public void submitTask(Runnable task) {
        executor.submit(task);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public ThreadPoolExecutor getExecutor() { return executor; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
