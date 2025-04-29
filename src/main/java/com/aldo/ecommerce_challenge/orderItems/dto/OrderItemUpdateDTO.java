package com.aldo.ecommerce_challenge.orderItems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

import java.util.Objects;

@Schema(description = "DTO representing the data needed to update an order item.")
public class OrderItemUpdateDTO {
  @Schema(
      description = "The ID of the order associated with the item to be updated.",
      example = "1")
  private Long orderId;

  @Schema(
      description = "The ID of the product associated with the order item to be updated.",
      example = "1")
  private Long productId;

  @Schema(
      description = "The quantity of the product in the order item to be updated.",
      example = "3")
  @Min(value = 0, message = "Quantity must be at least 0")
  private Integer quantity;

  public OrderItemUpdateDTO() {}

  public OrderItemUpdateDTO(Long orderId, Long productId, Integer quantity) {
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
    OrderItemUpdateDTO that = (OrderItemUpdateDTO) object;
    return Objects.equals(orderId, that.orderId)
        && Objects.equals(productId, that.productId)
        && Objects.equals(quantity, that.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, productId, quantity);
  }
}
