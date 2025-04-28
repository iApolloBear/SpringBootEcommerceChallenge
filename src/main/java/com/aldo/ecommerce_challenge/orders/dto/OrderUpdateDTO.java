package com.aldo.ecommerce_challenge.orders.dto;

import java.util.List;

public class OrderUpdateDTO {
  private List<Long> orderItemIds;

  public OrderUpdateDTO(List<Long> orderItemIds) {
    this.orderItemIds = orderItemIds;
  }

  public List<Long> getOrderItemIds() {
    return orderItemIds;
  }

  public void setOrderItemIds(List<Long> orderItemIds) {
    this.orderItemIds = orderItemIds;
  }
}
