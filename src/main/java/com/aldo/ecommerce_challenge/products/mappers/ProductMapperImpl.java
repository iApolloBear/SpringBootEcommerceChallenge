package com.aldo.ecommerce_challenge.products.mappers;

import com.aldo.ecommerce_challenge.products.dto.ProductCreateUpdateDTO;
import com.aldo.ecommerce_challenge.products.models.Product;

public class ProductMapperImpl implements ProductMapper {
  @Override
  public ProductCreateUpdateDTO toDto(Product product) {
    return new ProductCreateUpdateDTO(
        product.getName(), product.getDescription(), product.getPrice());
  }

  @Override
  public Product toProduct(ProductCreateUpdateDTO dto) {
    return new Product(dto.getName(), dto.getDescription(), dto.getPrice());
  }
}
