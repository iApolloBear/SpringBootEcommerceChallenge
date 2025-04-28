package com.aldo.ecommerce_challenge.orderItems.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
    description =
        "DTO for creating a new order item. Contains the necessary details for adding an item to an order.")
public class OrderItemCreateDTO {
  @Schema(description = "The ID of the order to which the item belongs.", example = "1")
  private Long orderId;

  @Schema(description = "The ID of the order to which the item belongs.", example = "1")
  private Long productId;

  @Schema(description = "The quantity of the product being ordered.", example = "2")
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
