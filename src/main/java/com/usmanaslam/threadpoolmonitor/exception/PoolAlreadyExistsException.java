package com.usmanaslam.threadpoolmonitor.exception;

public class PoolAlreadyExistsException extends RuntimeException {
    public PoolAlreadyExistsException(String message) {
        super(message);
    }
}
