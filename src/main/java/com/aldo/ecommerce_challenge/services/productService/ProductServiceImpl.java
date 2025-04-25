package com.aldo.ecommerce_challenge.services.productService;

import com.aldo.ecommerce_challenge.models.Product;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
  @Override
  public List<Product> findAll() {
    return List.of();
  }

  @Override
  public Optional<Product> findById() {
    return Optional.empty();
  }

  @Override
  public Product save(Product product) {
    return null;
  }

  @Override
  public Optional<Product> update(Long id, Product product) {
    return Optional.empty();
  }

  @Override
  public Optional<Product> delete(Long id) {
    return Optional.empty();
  }
}
