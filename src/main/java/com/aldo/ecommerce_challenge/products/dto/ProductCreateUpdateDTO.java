package com.aldo.ecommerce_challenge.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductCreateUpdateDTO {
  @Schema(description = "Name of the product", example = "GUTS")
  private String name;

  @Schema(description = "Description of the product", example = "Olivia Rodrigo Album")
  private String description;

  @Schema(description = "Price of the product", example = "1753.89")
  private BigDecimal price;

  public ProductCreateUpdateDTO() {}

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
