package com.usmanaslam.threadpoolmonitor.controller;

import com.usmanaslam.threadpoolmonitor.dto.VirtualThreadBenchmarkResult;
import com.usmanaslam.threadpoolmonitor.service.VirtualThreadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/virtual-threads")
@Tag(name = "Virtual Threads Benchmark")
public class VirtualThreadController {

    private final VirtualThreadService virtualThreadService;

    public VirtualThreadController(VirtualThreadService virtualThreadService) {
        this.virtualThreadService = virtualThreadService;
    }

    @PostMapping("/benchmark")
    @Operation(summary = "Run benchmark comparing virtual and platform threads")
    public VirtualThreadBenchmarkResult benchmark(
            @RequestParam(defaultValue = "1000") int taskCount,
            @RequestParam(defaultValue = "SLEEP") String taskType,
            @RequestParam(defaultValue = "100") int durationMs) {
        return virtualThreadService.benchmark(taskCount, taskType, durationMs);
    }
}
