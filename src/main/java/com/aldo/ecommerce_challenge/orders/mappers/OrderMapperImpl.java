package com.aldo.ecommerce_challenge.orders.mappers;

import com.aldo.ecommerce_challenge.orderItems.mappers.OrderItemMapper;
import com.aldo.ecommerce_challenge.orders.dto.OrderDTO;
import com.aldo.ecommerce_challenge.orders.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl implements OrderMapper {
  private final OrderItemMapper orderItemMapper;

  public OrderMapperImpl(OrderItemMapper orderItemMapper) {
    this.orderItemMapper = orderItemMapper;
  }

  @Override
  public OrderDTO toDto(Order order) {
    return new OrderDTO(
        order.getId(),
        order.getTotal(),
        order.getOrderItems().stream().map(this.orderItemMapper::toOrderItemDto).toList());
  }

  @Override
  public Order toOrder(OrderDTO dto) {
    Order order = new Order();
    order.setId(dto.getId());
    order.setTotal(dto.getTotal());
    order.setOrderItems(
        dto.getOrderItems().stream().map(this.orderItemMapper::toOrderItem).toList());
    return order;
  }
}
