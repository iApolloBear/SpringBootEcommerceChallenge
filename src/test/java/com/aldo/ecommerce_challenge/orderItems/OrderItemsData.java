package com.aldo.ecommerce_challenge.orderItems;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;

import java.math.BigDecimal;

public class OrderItemsData {
  public static OrderItemDTO createOrderItemDTOOne() {
    return new OrderItemDTO(1L, 1L, 1L, 1, new BigDecimal("938"));
  }

  public static OrderItemDTO createOrderItemDTOTwo() {
    return new OrderItemDTO(2L, 1L, 2L, 1, new BigDecimal("1499"));
  }

  public static OrderItemDTO createOrderItemDTOThree() {
    return new OrderItemDTO(3L, 2L, 1L, 2, new BigDecimal("1876"));
  }

  public static OrderItemDTO createOrderItemDTOFour() {
    return new OrderItemDTO(4L, 2L, 2L, 2, new BigDecimal("2998"));
  }

  public static OrderItemCreateDTO createOrderItemCreateDTO() {
    return new OrderItemCreateDTO(1L, 2L, 2);
  }
}
