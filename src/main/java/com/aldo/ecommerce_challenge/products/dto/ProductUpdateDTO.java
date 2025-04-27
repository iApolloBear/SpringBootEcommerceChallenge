package com.aldo.ecommerce_challenge.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductUpdateDTO {
  @Schema(description = "New name for the product (optional)", example = "SOUR")
  private Optional<String> name = Optional.empty();

  @Schema(description = "New desciption for the product (optional)")
  private Optional<String> description = Optional.empty();

  @Schema(description = "New price for the product (optional)", example = "800")
  private Optional<BigDecimal> price = Optional.empty();

  public ProductUpdateDTO() {}

  public Optional<String> getName() {
    return name;
  }

  public void setName(Optional<String> name) {
    this.name = name;
  }

  public Optional<String> getDescription() {
    return description;
  }

  public void setDescription(Optional<String> description) {
    this.description = description;
  }

  public Optional<BigDecimal> getPrice() {
    return price;
  }

  public void setPrice(Optional<BigDecimal> price) {
    this.price = price;
  }
}
