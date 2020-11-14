package com.backbase.openbank.service.controller;

import com.backbase.openbank.service.exception.BackbaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@SuppressWarnings("unused")
public class ControllerExceptionHandler {
    private static final Logger LOG = LogManager.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(BackbaseException.class)
    public ResponseEntity<Object> handleBackbaseException(BackbaseException be) {
        LOG.error("Received exception: " + be.getMessage(), be);
        return new ResponseEntity<>(be.getMessage(), be.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        LOG.error("Unexpected exception: " + e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
