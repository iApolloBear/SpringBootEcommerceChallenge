package com.aldo.ecommerce_challenge.orders.exceptions;

public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException(Long id) {
    super(String.format("Order with ID %d not found", id));
  }
}
