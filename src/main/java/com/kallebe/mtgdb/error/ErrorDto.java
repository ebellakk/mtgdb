package com.kallebe.mtgdb.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record ErrorDto(Integer status, String message, LocalDateTime timestamp) {
    public ErrorDto(HttpStatus status, String message) {
        this(status.value(), message, LocalDateTime.now(ZoneOffset.UTC));
    }
}
