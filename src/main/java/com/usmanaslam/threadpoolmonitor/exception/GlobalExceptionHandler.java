package com.usmanaslam.threadpoolmonitor.exception;

import com.usmanaslam.threadpoolmonitor.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PoolNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePoolNotFound(PoolNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PoolAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePoolAlreadyExists(PoolAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), System.currentTimeMillis()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
