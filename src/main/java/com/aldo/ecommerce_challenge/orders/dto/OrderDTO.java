package com.aldo.ecommerce_challenge.orders.dto;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(name = "OrderDTO", description = "DTO used to represent product entity")
public class OrderDTO {
  @Schema(description = "Unique identifier for the order", example = "1")
  private Long id;

  @Schema(description = "Total price of the order", example = "150.75")
  private BigDecimal total;

  @Schema(description = "List of order items associated with this order")
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
