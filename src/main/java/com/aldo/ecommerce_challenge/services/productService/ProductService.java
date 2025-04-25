package com.aldo.ecommerce_challenge.services.productService;

import com.aldo.ecommerce_challenge.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
  List<Product> findAll();

  Optional<Product> findById();

  Product save(Product product);

  Optional<Product> update(Long id, Product product);

  Optional<Product> delete(Long id);
}
