package com.aldo.ecommerce_challenge.orderItems.dto;

public class OrderItemCreateUpdateDTO {
  private Long orderId;
  private Long productId;
  private Integer quantity;

  public OrderItemCreateUpdateDTO() {}

  public OrderItemCreateUpdateDTO(Long orderId, Long productId, Integer quantity) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
