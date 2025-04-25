package com.aldo.ecommerce_challenge.repositories;

import com.aldo.ecommerce_challenge.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {}
