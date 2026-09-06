package com.usmanaslam.threadpoolmonitor.service;

import com.usmanaslam.threadpoolmonitor.dto.VirtualThreadBenchmarkResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class VirtualThreadService {

    public VirtualThreadBenchmarkResult benchmark(int taskCount, String taskType, int durationMs) {
        long platformTime = runBenchmark(Executors.newFixedThreadPool(100), taskCount, taskType, durationMs);
        long virtualTime = runBenchmark(Executors.newVirtualThreadPerTaskExecutor(), taskCount, taskType, durationMs);
        
        double speedup = (double) platformTime / (virtualTime > 0 ? virtualTime : 1);
        
        return new VirtualThreadBenchmarkResult(taskCount, platformTime, virtualTime, speedup, taskType);
    }

    private long runBenchmark(ExecutorService executor, int taskCount, String taskType, int durationMs) {
        long start = System.currentTimeMillis();
        List<Future<?>> futures = new ArrayList<>();
        
        for (int i = 0; i < taskCount; i++) {
            futures.add(executor.submit(() -> {
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
            }));
        }
        
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        executor.shutdown();
        return System.currentTimeMillis() - start;
    }
}
