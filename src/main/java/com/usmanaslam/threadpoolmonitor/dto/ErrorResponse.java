package com.usmanaslam.threadpoolmonitor.dto;

public record ErrorResponse(
        int status,
        String message,
        long timestamp
) {}
