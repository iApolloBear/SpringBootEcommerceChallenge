package com.aldo.ecommerce_challenge.orders.services;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
  List<Order> findAll();

  Optional<Order> findById(Long id);

  Order save(List<OrderItem> orderItems);

  Optional<Order> update(Long id, List<OrderItem> orderItems);

  Optional<Order> delete(Long id);
}
