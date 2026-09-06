package com.usmanaslam.threadpoolmonitor.controller;

import com.usmanaslam.threadpoolmonitor.dto.PoolConfigRequest;
import com.usmanaslam.threadpoolmonitor.dto.PoolInfo;
import com.usmanaslam.threadpoolmonitor.dto.PoolMetrics;
import com.usmanaslam.threadpoolmonitor.service.PoolManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pools")
@Tag(name = "Thread Pool Management")
public class PoolController {

    private final PoolManagerService poolManagerService;

    public PoolController(PoolManagerService poolManagerService) {
        this.poolManagerService = poolManagerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new thread pool")
    public void createPool(@RequestBody PoolConfigRequest request) {
        poolManagerService.createPool(request);
    }

    @GetMapping
    @Operation(summary = "List all thread pools")
    public List<PoolInfo> getAllPools() {
        return poolManagerService.getAllPools();
    }

    @GetMapping("/{name}/metrics")
    @Operation(summary = "Get metrics for a specific pool")
    public PoolMetrics getMetrics(@PathVariable String name) {
        return poolManagerService.getMetrics(name);
    }

    @GetMapping("/metrics")
    @Operation(summary = "Get metrics for all pools")
    public List<PoolMetrics> getAllMetrics() {
        return poolManagerService.getAllMetrics();
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Shutdown and remove a pool")
    public void shutdownPool(@PathVariable String name) {
        poolManagerService.shutdownPool(name);
    }
}
