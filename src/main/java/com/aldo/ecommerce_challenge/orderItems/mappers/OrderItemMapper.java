package com.aldo.ecommerce_challenge.orderItems.mappers;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTOWithProduct;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;

public interface OrderItemMapper {
  OrderItem toOrderItem(OrderItemDTO dto);

  OrderItem toOrderItem(OrderItemDTOWithProduct dto);

  OrderItem toOrderItem(OrderItemCreateDTO dto);

  OrderItem toOrderItem(OrderItemUpdateDTO dto);

  OrderItemDTO toOrderItemDto(OrderItem orderItem);

  OrderItemDTOWithProduct toOrderItemDtoWithProduct(OrderItem orderItem);

  OrderItemCreateDTO toOrderItemCreateDto(OrderItem orderItem);

  OrderItemUpdateDTO toOrderItemUpdateDto(OrderItem orderItem);
}
