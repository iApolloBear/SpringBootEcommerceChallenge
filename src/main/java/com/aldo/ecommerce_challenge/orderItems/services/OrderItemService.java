package com.aldo.ecommerce_challenge.orderItems.services;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
  List<OrderItemDTO> findAll();

  Optional<OrderItemDTO> findById(Long id);

  OrderItemDTO save(OrderItemCreateUpdateDTO orderItem);

  Optional<OrderItemDTO> update(Long id, OrderItemCreateUpdateDTO orderItem);

  Optional<OrderItemDTO> delete(Long id);
}
