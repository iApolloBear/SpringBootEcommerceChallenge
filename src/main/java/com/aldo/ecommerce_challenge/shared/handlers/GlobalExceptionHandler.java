package com.aldo.ecommerce_challenge.shared.controllers;

import com.aldo.ecommerce_challenge.shared.models.ErrorResponseWithMessages;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseWithMessages> handleValidationException(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    List<String> validationErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .toList();

    ErrorResponseWithMessages errorResponse =
        new ErrorResponseWithMessages(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            validationErrors,
            request.getRequestURI());

    return ResponseEntity.badRequest().body(errorResponse);
  }
}
