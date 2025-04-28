package com.aldo.ecommerce_challenge.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(name = "ProductCreateDTO", description = "DTO used to create a new product")
public class ProductCreateDTO {
  @Schema(description = "Name of the product", example = "GUTS")
  @NotNull(message = "Product name is required")
  @NotBlank(message = "Product name must not be blank.")
  @Size(min = 3, message = "Product name must be at least 3 characters long.")
  private String name;

  @Schema(description = "Description of the product", example = "Olivia Rodrigo Album")
  private String description;

  @Schema(description = "Price of the product", example = "1753.89")
  @NotNull(message = "Product price is required.")
  @DecimalMin(value = "0", message = "Price must be greater than or equal to 0.")
  private BigDecimal price;

  public ProductCreateDTO() {}

  public ProductCreateDTO(String name, String description, BigDecimal price) {
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
    ProductCreateDTO that = (ProductCreateDTO) object;
    return Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, price);
  }
}
