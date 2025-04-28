package com.aldo.ecommerce_challenge.orderItems.dto;

import java.util.Objects;

public class OrderItemCreateDTO {
  private Long orderId;
  private Long productId;
  private Integer quantity;

  public OrderItemCreateDTO() {}

  public OrderItemCreateDTO(Long orderId, Long productId, Integer quantity) {
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

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    OrderItemCreateDTO that = (OrderItemCreateDTO) object;
    return Objects.equals(orderId, that.orderId)
        && Objects.equals(productId, that.productId)
        && Objects.equals(quantity, that.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, productId, quantity);
  }
}
