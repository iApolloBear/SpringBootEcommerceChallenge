package com.aldo.ecommerce_challenge.products.services;

import com.aldo.ecommerce_challenge.products.dto.ProductCreateDTO;
import com.aldo.ecommerce_challenge.products.dto.ProductUpdateDTO;
import com.aldo.ecommerce_challenge.products.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
  List<Product> findAll();

  Optional<Product> findById(Long id);

  Product save(ProductCreateDTO dto);

  Optional<Product> update(Long id, ProductUpdateDTO dto);

  Optional<Product> delete(Long id);
}
