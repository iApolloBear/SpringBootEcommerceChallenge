package com.aldo.ecommerce_challenge.orderItems.handlers;

import com.aldo.ecommerce_challenge.orderItems.exceptions.OrderItemNotFoundException;
import com.aldo.ecommerce_challenge.shared.models.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class OrderItemExceptionHandler {
  @ExceptionHandler(OrderItemNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleOrderItemNotFoundException(
      OrderItemNotFoundException ex, HttpServletRequest request) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}
