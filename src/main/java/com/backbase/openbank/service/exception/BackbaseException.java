package com.backbase.openbank.service.exception;


import org.springframework.http.HttpStatus;

public class BackbaseException extends Exception {

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public BackbaseException(String message) {
        super(message);
    }

    public BackbaseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
