package com.usmanaslam.threadpoolmonitor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.usmanaslam.threadpoolmonitor.dto.PoolMetrics;
import com.usmanaslam.threadpoolmonitor.websocket.MetricsWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricsCollectorService {

    private final PoolManagerService poolManagerService;
    private final MetricsWebSocketHandler webSocketHandler;
    private final ObjectMapper objectMapper;

    public MetricsCollectorService(PoolManagerService poolManagerService, MetricsWebSocketHandler webSocketHandler) {
        this.poolManagerService = poolManagerService;
        this.webSocketHandler = webSocketHandler;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Scheduled(fixedRate = 1000)
    public void collectAndBroadcast() {
        List<PoolMetrics> metrics = poolManagerService.getAllMetrics();
        if (!metrics.isEmpty()) {
            try {
                String json = objectMapper.writeValueAsString(metrics);
                webSocketHandler.broadcast(json);
            } catch (JsonProcessingException e) {
                // Ignore
            }
        }
    }
}
