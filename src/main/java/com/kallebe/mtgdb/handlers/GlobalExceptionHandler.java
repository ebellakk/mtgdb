package com.kallebe.mtgdb.handlers;

import com.kallebe.mtgdb.error.ErrorDto;
import com.kallebe.mtgdb.error.exception.ScryfallIntegrationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ScryfallIntegrationException.class)
    public ResponseEntity<ErrorDto> generateScryfallIntegrationExceptionResponse(ScryfallIntegrationException ex) {
        ErrorDto errorDTO = new ErrorDto(ex.getStatus(), ex.getMessage());
        return new ResponseEntity<ErrorDto>(errorDTO, ex.getStatus());
    }
}
