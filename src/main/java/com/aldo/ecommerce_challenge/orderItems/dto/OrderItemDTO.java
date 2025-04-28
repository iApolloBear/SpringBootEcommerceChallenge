package com.aldo.ecommerce_challenge.orderItems.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(
    description =
        "DTO representing an order item with details including the product id, quantity, and price.")
public class OrderItemDTO {
  @Schema(description = "The unique identifier of the order item.", example = "1")
  private Long id;

  @Schema(description = "The ID of the order to which the item belongs.", example = "1")
  private Long orderId;

  @Schema(description = "The ID of the product associated with this order item.", example = "1")
  private Long productId;

  @Schema(description = "The quantity of the product in this order item.", example = "2")
  private Integer quantity;

  @Schema(
      description = "The price of a single unit of the product in this order item.",
      example = "1600")
  private BigDecimal price;

  public OrderItemDTO(Long id, Long orderId, Long productId, Integer quantity, BigDecimal price) {
    this.id = id;
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    OrderItemDTO that = (OrderItemDTO) object;
    return Objects.equals(id, that.id)
        && Objects.equals(orderId, that.orderId)
        && Objects.equals(productId, that.productId)
        && Objects.equals(quantity, that.quantity)
        && Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderId, productId, quantity, price);
  }
}
