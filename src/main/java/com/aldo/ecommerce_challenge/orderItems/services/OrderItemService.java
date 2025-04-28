package com.aldo.ecommerce_challenge.orderItems.services;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
  List<OrderItemDTO> findAll();

  Optional<OrderItemDTO> findById(Long id);

  OrderItemDTO save(OrderItemCreateDTO orderItem);

  Optional<OrderItemDTO> update(Long id, OrderItemUpdateDTO orderItem);

  Optional<OrderItemDTO> delete(Long id);
}
