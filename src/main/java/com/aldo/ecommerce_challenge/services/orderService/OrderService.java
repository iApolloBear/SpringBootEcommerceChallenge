package com.aldo.ecommerce_challenge.services.orderService;

import com.aldo.ecommerce_challenge.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
  List<Order> findAll();

  Optional<Order> findById(Long id);

  Order save(Order order);

  Optional<Order> update(Long id, Order order);

  Optional<Order> delete(Long id);
}
