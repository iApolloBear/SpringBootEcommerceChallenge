package com.aldo.ecommerce_challenge.orderItems.exceptions;

public class OrderItemNotFoundException extends RuntimeException {
  public OrderItemNotFoundException(Long id) {
    super(String.format("Order item with ID %d not found", id));
  }
}
