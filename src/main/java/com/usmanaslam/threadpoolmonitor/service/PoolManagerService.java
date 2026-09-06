package com.usmanaslam.threadpoolmonitor.service;

import com.usmanaslam.threadpoolmonitor.dto.PoolConfigRequest;
import com.usmanaslam.threadpoolmonitor.dto.PoolInfo;
import com.usmanaslam.threadpoolmonitor.dto.PoolMetrics;
import com.usmanaslam.threadpoolmonitor.exception.PoolAlreadyExistsException;
import com.usmanaslam.threadpoolmonitor.exception.PoolNotFoundException;
import com.usmanaslam.threadpoolmonitor.model.ManagedPool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class PoolManagerService {
    private final ConcurrentHashMap<String, ManagedPool> pools = new ConcurrentHashMap<>();

    public void createPool(PoolConfigRequest request) {
        if (pools.containsKey(request.name())) {
            throw new PoolAlreadyExistsException("Pool with name " + request.name() + " already exists.");
        }
        ManagedPool pool = new ManagedPool(request.name(), request.coreSize(), request.maxSize(), request.queueCapacity(), request.description());
        pools.put(request.name(), pool);
    }

    public ManagedPool getPool(String name) {
        ManagedPool pool = pools.get(name);
        if (pool == null) {
            throw new PoolNotFoundException("Pool " + name + " not found.");
        }
        return pool;
    }

    public List<PoolInfo> getAllPools() {
        return pools.values().stream()
                .map(p -> new PoolInfo(
                        p.getName(),
                        p.getExecutor().getCorePoolSize(),
                        p.getExecutor().getMaximumPoolSize(),
                        p.getExecutor().getQueue().remainingCapacity() + p.getExecutor().getQueue().size(),
                        p.getDescription(),
                        p.getExecutor().isShutdown() ? "SHUTDOWN" : "RUNNING"
                )).collect(Collectors.toList());
    }

    public PoolMetrics getMetrics(String name) {
        return getPool(name).getMetrics();
    }

    public List<PoolMetrics> getAllMetrics() {
        return pools.values().stream()
                .map(ManagedPool::getMetrics)
                .collect(Collectors.toList());
    }

    public void shutdownPool(String name) {
        ManagedPool pool = getPool(name);
        pool.shutdown();
        pools.remove(name);
    }
}
