package com.aldo.ecommerce_challenge.products;

import com.aldo.ecommerce_challenge.products.models.Product;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductsData {
  public static Optional<Product> createProductOne() {
    Product product = new Product(1L, "GUTS", "Olivia Rodrigo Album", new BigDecimal("938"));
    return Optional.of(product);
  }

  public static Optional<Product> createProductTwo() {
    Product product = new Product(2L, "F-1 Trillion", "Post Malone Album", new BigDecimal("1499"));
    return Optional.of(product);
  }
}
