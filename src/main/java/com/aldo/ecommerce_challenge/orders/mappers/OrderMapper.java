package com.aldo.ecommerce_challenge.orders.mappers;

import com.aldo.ecommerce_challenge.orders.dto.OrderDTO;
import com.aldo.ecommerce_challenge.orders.models.Order;

public interface OrderMapper {
  OrderDTO toDto(Order order);

  Order toOrder(OrderDTO dto);
}
