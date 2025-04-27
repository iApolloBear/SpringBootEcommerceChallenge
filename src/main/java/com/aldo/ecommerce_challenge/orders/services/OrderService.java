package com.aldo.ecommerce_challenge.orders.services;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.dto.OrderCreateUpdateDTO;
import com.aldo.ecommerce_challenge.orders.dto.OrderDTO;
import com.aldo.ecommerce_challenge.orders.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
  List<OrderDTO> findAll();

  Optional<OrderDTO> findById(Long id);

  OrderDTO save();

  Optional<OrderDTO> update(Long id, OrderCreateUpdateDTO dto);

  Optional<OrderDTO> delete(Long id);
}
