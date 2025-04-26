package com.aldo.ecommerce_challenge.orders.models;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(nullable = false)
  private BigDecimal total = new BigDecimal(0);

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

  public Order() {}

  public Order(Long id, List<OrderItem> orderItems) {
    this.id = id;
    this.orderItems = orderItems;
    calculateTotal();
  }

  public Order(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
    calculateTotal();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
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
  }

  public void addItem(OrderItem item) {
    this.orderItems.add(item);
    item.setOrder(this);
    calculateTotal();
  }

  private void calculateTotal() {
    this.total =
        this.orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
