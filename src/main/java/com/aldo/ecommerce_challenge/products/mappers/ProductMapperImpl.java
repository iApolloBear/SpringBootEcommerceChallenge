package com.aldo.ecommerce_challenge.products.mappers;

import com.aldo.ecommerce_challenge.products.dto.ProductCreateDTO;
import com.aldo.ecommerce_challenge.products.dto.ProductUpdateDTO;
import com.aldo.ecommerce_challenge.products.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper {
  @Override
  public ProductCreateDTO toCreateDto(Product product) {
    return new ProductCreateDTO(product.getName(), product.getDescription(), product.getPrice());
  }

  @Override
  public ProductUpdateDTO toUpdateDto(Product product) {
    return new ProductUpdateDTO(product.getName(), product.getDescription(), product.getPrice());
  }

  @Override
  public Product toProduct(ProductCreateDTO dto) {
    return new Product(dto.getName(), dto.getDescription(), dto.getPrice());
  }

  @Override
  public Product toProduct(ProductUpdateDTO dto) {
    return new Product(dto.getName(), dto.getDescription(), dto.getPrice());
  }
}
