package com.aldo.ecommerce_challenge.products.exceptions;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(Long id) {
    super(String.format("Product with ID %d not found", id));
  }
}
