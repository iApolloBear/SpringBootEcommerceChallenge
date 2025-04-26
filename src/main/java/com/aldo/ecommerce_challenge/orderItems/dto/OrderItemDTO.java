package com.aldo.ecommerce_challenge.orderItems.dto;

import com.aldo.ecommerce_challenge.products.models.Product;

import java.math.BigDecimal;

public class OrderItemDTO {
  private Long id;
  private Long orderId;
  private Product product;
  private Integer quantity;
  private BigDecimal price;

  public OrderItemDTO(
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
}
