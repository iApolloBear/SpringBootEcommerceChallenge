package com.aldo.ecommerce_challenge.shared.exceptions;

import org.springframework.http.HttpStatus;

public class BaseHttpException extends RuntimeException {
  private final HttpStatus status;

  public BaseHttpException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
