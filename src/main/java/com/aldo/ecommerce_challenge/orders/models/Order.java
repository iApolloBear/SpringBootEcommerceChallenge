package com.aldo.ecommerce_challenge.orders.models;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private BigDecimal total;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems;

  public Order() {
    this.total = BigDecimal.ZERO;
    this.orderItems = new ArrayList<>();
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

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
    this.total = this.calculateTotal();
  }

  public void addItem(OrderItem item) {
    this.orderItems.add(item);
    item.setOrder(this);
    this.total = this.calculateTotal();
  }

  public void removeItem(OrderItem item) {
    this.orderItems.remove(item);
    item.setOrder(null);
    this.total = this.calculateTotal();
  }

  private BigDecimal calculateTotal() {
    return this.orderItems.stream()
        .map(OrderItem::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Order order = (Order) object;
    return Objects.equals(id, order.id)
        && Objects.equals(total, order.total)
        && Objects.equals(orderItems, order.orderItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, total);
  }
}
