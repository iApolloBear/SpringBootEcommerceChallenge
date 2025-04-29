package com.aldo.ecommerce_challenge.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

@Schema(description = "DTO to update an order with a list of order item IDs")
public class OrderUpdateDTO {
  @Schema(description = "List of order item IDs to associate with the order", example = "[1, 2, 3]")
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

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    OrderUpdateDTO that = (OrderUpdateDTO) object;
    return Objects.equals(orderItemIds, that.orderItemIds);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(orderItemIds);
  }
}
