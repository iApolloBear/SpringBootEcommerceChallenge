package com.aldo.ecommerce_challenge.orders.services;

import com.aldo.ecommerce_challenge.orders.dto.OrderUpdateDTO;
import com.aldo.ecommerce_challenge.orders.dto.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {
  List<OrderDTO> findAll();

  Optional<OrderDTO> findById(Long id);

  OrderDTO save();

  Optional<OrderDTO> update(Long id, OrderUpdateDTO dto);

  Optional<OrderDTO> delete(Long id);
}
