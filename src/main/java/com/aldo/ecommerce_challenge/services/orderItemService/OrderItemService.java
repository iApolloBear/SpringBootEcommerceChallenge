package com.aldo.ecommerce_challenge.services.orderItemService;

import com.aldo.ecommerce_challenge.models.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
  List<OrderItem> findAll();

  Optional<OrderItem> findById(Long id);

  OrderItem save(OrderItem orderItem);

  Optional<OrderItem> update(Long id, OrderItem orderItem);

  Optional<OrderItem> delete(Long id);
}
