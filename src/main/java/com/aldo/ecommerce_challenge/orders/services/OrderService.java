package com.aldo.ecommerce_challenge.orders.services;

import com.aldo.ecommerce_challenge.orders.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
  List<Order> findAll();

  Optional<Order> findById(Long id);

  Order save(Order order);

  Optional<Order> update(Long id, Order order);

  Optional<Order> delete(Long id);
}
