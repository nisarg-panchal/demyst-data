package com.nisarg.demyst.controller.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

@RestControllerAdvice
@Slf4j
public class RestResponseExceptionHandler {

  @ExceptionHandler(InternalServerError.class)
  @ResponseStatus(value = INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleErrorScenario() {
    return ResponseEntity.internalServerError().body(
        ErrorResponse.builder(
                new LoanApplicationException(), INTERNAL_SERVER_ERROR,
                "Sorry for Inconvenience")
            .build());
  }

  static class LoanApplicationException extends RuntimeException {

  }
}
