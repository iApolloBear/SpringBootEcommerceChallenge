package com.aldo.ecommerce_challenge.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(
    name = "ProductUpdateDTO",
    description =
        "DTO used to update an existing product. This DTO allows you to modify the product's name, description, and price.")
public class ProductUpdateDTO {
  @Schema(description = "New name for the product (optional)", example = "SOUR")
  private String name;

  @Schema(description = "New description for the product (optional)", example = "Olivia Rodrigo Album")
  private String description;

  @Schema(description = "New price for the product (optional)", example = "800")
  private BigDecimal price;

  public ProductUpdateDTO() {}

  public ProductUpdateDTO(String name, String description, BigDecimal price) {
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
    ProductUpdateDTO that = (ProductUpdateDTO) object;
    return Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, price);
  }
}
