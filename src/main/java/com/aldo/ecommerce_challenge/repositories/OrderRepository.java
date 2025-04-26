package com.aldo.ecommerce_challenge.repositories;

import com.aldo.ecommerce_challenge.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {}
