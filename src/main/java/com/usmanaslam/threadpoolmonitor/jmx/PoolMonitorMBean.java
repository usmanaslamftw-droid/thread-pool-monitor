package com.usmanaslam.threadpoolmonitor.jmx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.usmanaslam.threadpoolmonitor.dto.PoolInfo;
import com.usmanaslam.threadpoolmonitor.service.PoolManagerService;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ManagedResource(objectName = "com.usmanaslam.threadpoolmonitor:type=PoolMonitor", description = "Monitor Thread Pools")
public class PoolMonitorMBean implements PoolMonitorMXBean {

    private final PoolManagerService poolManagerService;
    private final ObjectMapper objectMapper;

    public PoolMonitorMBean(PoolManagerService poolManagerService) {
        this.poolManagerService = poolManagerService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Override
    public int getActivePoolCount() {
        return poolManagerService.getAllPools().size();
    }

    @Override
    public List<String> getPoolNames() {
        return poolManagerService.getAllPools().stream().map(PoolInfo::name).collect(Collectors.toList());
    }

    @Override
    public String getPoolMetrics(String name) {
        try {
            return objectMapper.writeValueAsString(poolManagerService.getMetrics(name));
        } catch (Exception e) {
            return "Error retrieving metrics for " + name;
        }
    }
}
