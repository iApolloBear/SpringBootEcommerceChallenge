package com.aldo.ecommerce_challenge.orders.repositories;

import com.aldo.ecommerce_challenge.orders.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {}
