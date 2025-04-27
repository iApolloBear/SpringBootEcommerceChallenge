package com.aldo.ecommerce_challenge.orders.dto;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;

import java.util.List;

public class OrderCreateUpdateDTO {
  private List<OrderItemDTO> orderItems;

  public OrderCreateUpdateDTO(List<OrderItemDTO> orderItems) {
    this.orderItems = orderItems;
  }

  public List<OrderItemDTO> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItemDTO> orderItems) {
    this.orderItems = orderItems;
  }
}
