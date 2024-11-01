package com.example.bank.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Slf4j
@EnableWebMvc
@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler({Exception.class, Throwable.class})
  public ResponseEntity<Object> customHandling(Exception e) {
    log.error("Exception occurred: {}", e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }
}
