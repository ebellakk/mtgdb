package com.kallebe.mtgdb.error.exception;

import org.springframework.http.HttpStatus;

public class ScryfallIntegrationException extends RuntimeException {

    private final HttpStatus status;

    public ScryfallIntegrationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
