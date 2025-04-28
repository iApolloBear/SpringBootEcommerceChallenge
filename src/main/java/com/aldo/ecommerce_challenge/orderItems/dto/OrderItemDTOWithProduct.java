package com.aldo.ecommerce_challenge.orderItems.dto;

import com.aldo.ecommerce_challenge.products.models.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(description = "DTO representing an order item with a product, quantity, and price details.")
public class OrderItemDTOWithProduct {
  @Schema(description = "The unique identifier of the order item.", example = "1")
  private Long id;

  @Schema(description = "The ID of the order to which the item belongs.", example = "100")
  private Long orderId;

  @Schema(description = "The product associated with the order item, including product details.")
  private Product product;

  @Schema(description = "The quantity of the product in this order item.", example = "2")
  private Integer quantity;

  @Schema(
      description = "The price of a single unit of the product in this order item.",
      example = "800")
  private BigDecimal price;

  public OrderItemDTOWithProduct(
      Long id, Long orderId, Product product, Integer quantity, BigDecimal price) {
    this.id = id;
    this.orderId = orderId;
    this.product = product;
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

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
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
    OrderItemDTOWithProduct that = (OrderItemDTOWithProduct) object;
    return Objects.equals(id, that.id)
        && Objects.equals(orderId, that.orderId)
        && Objects.equals(product, that.product)
        && Objects.equals(quantity, that.quantity)
        && Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderId, product, quantity, price);
  }
}
