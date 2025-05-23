package com.aldo.ecommerce_challenge.products.repositories;

import com.aldo.ecommerce_challenge.products.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {}
