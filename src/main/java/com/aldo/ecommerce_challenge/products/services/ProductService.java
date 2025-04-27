package com.aldo.ecommerce_challenge.products.services;

import com.aldo.ecommerce_challenge.products.dto.ProductCreateUpdateDTO;
import com.aldo.ecommerce_challenge.products.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
  List<Product> findAll();

  Optional<Product> findById(Long id);

  Product save(ProductCreateUpdateDTO dto);

  Optional<Product> update(Long id, ProductCreateUpdateDTO dto);

  Optional<Product> delete(Long id);
}
