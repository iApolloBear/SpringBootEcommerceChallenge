package com.aldo.ecommerce_challenge.orders.dto;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;

import java.math.BigDecimal;
import java.util.List;

public class OrderDTO {
  private Long id;
  private BigDecimal total;
  private List<OrderItemDTO> orderItems;

  public OrderDTO(Long id, BigDecimal total, List<OrderItemDTO> orderItems) {
    this.id = id;
    this.total = total;
    this.orderItems = orderItems;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public List<OrderItemDTO> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItemDTO> orderItems) {
    this.orderItems = orderItems;
  }
}
