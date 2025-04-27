package com.aldo.ecommerce_challenge.products.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductCreateUpdateDTO {
  private String name;
  private String description;
  private BigDecimal price;

  public ProductCreateUpdateDTO(String name, String description, BigDecimal price) {
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
    ProductCreateUpdateDTO that = (ProductCreateUpdateDTO) object;
    return Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, price);
  }
}
