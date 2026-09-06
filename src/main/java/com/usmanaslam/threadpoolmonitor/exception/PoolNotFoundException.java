package com.usmanaslam.threadpoolmonitor.exception;

public class PoolNotFoundException extends RuntimeException {
    public PoolNotFoundException(String message) {
        super(message);
    }
}
