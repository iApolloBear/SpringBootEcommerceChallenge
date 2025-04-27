package com.aldo.ecommerce_challenge.orderItems.mappers;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTOWithProduct;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;

public interface OrderItemMapper {
  OrderItem toOrderItem(OrderItemDTO dto);

  OrderItem toOrderItem(OrderItemDTOWithProduct dto);

  OrderItem toOrderItem(OrderItemCreateUpdateDTO dto);

  OrderItemDTO toOrderItemDto(OrderItem orderItem);

  OrderItemDTOWithProduct toOrderItemDtoWithProduct(OrderItem orderItem);

  OrderItemCreateUpdateDTO toOrderItemCreateUpdateDto(OrderItem orderItem);
}
