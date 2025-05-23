package com.aldo.ecommerce_challenge.products.services;

import com.aldo.ecommerce_challenge.products.dto.ProductCreateDTO;
import com.aldo.ecommerce_challenge.products.dto.ProductUpdateDTO;
import com.aldo.ecommerce_challenge.products.mappers.ProductMapper;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
  private final ProductRepository repository;
  private final ProductMapper mapper;

  public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Product> findAll() {
    return (List<Product>) this.repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Product> findById(Long id) {
    return this.repository.findById(id);
  }

  @Override
  @Transactional
  public Product save(ProductCreateDTO dto) {
    return this.repository.save(this.mapper.toProduct(dto));
  }

  @Override
  @Transactional
  public Optional<Product> update(Long id, ProductUpdateDTO dto) {
    return repository
        .findById(id)
        .map(
            product -> {
              if (dto.getName() != null) {
                product.setName(dto.getName());
              }

              if (dto.getDescription() != null) {
                product.setDescription(dto.getDescription());
              }

              if (dto.getPrice() != null) {
                product.setPrice(dto.getPrice());
              }
              return this.repository.save(product);
            });
  }

  @Override
  @Transactional
  public Optional<Product> delete(Long id) {
    return repository
        .findById(id)
        .map(
            product -> {
              this.repository.delete(product);
              return product;
            });
  }
}
