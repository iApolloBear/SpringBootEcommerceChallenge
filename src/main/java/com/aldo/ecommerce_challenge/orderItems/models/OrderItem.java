package com.aldo.ecommerce_challenge.orderItems.models;

import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.products.models.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_items")
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToOne(optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private BigDecimal price = new BigDecimal(0);

  public OrderItem() {}

  public OrderItem(Order order, Product product, Integer quantity) {
    this.order = order;
    this.product = product;
    this.quantity = quantity;
    this.price = this.calculatePrice();
  }

  public OrderItem(Long id, Order order, Product product, Integer quantity) {
    this.id = id;
    this.order = order;
    this.product = product;
    this.quantity = quantity;
    this.price = this.calculatePrice();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
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

  private BigDecimal calculatePrice() {
    return this.product.getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    OrderItem orderItem = (OrderItem) object;
    return Objects.equals(id, orderItem.id)
        && Objects.equals(order, orderItem.order)
        && Objects.equals(product, orderItem.product)
        && Objects.equals(quantity, orderItem.quantity)
        && Objects.equals(price, orderItem.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, product, quantity, price);
  }
}
