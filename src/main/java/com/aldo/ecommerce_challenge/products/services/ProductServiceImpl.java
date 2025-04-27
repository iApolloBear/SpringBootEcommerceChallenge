package com.aldo.ecommerce_challenge.products.services;

import com.aldo.ecommerce_challenge.products.dto.ProductCreateUpdateDTO;
import com.aldo.ecommerce_challenge.products.mappers.ProductCreateUpdateMapper;
import com.aldo.ecommerce_challenge.products.mappers.ProductCreateUpdateMapperImpl;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
  private final ProductRepository repository;
  private final ProductCreateUpdateMapper mapper = new ProductCreateUpdateMapperImpl();

  public ProductServiceImpl(ProductRepository repository) {
    this.repository = repository;
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
  public Product save(ProductCreateUpdateDTO dto) {
    return this.repository.save(this.mapper.toProduct(dto));
  }

  @Override
  public Optional<Product> update(Long id, ProductCreateUpdateDTO dto) {
    return repository
        .findById(id)
        .map(
            productDb -> {
              productDb.setName(dto.getName());
              productDb.setDescription(dto.getDescription());
              productDb.setPrice(dto.getPrice());
              return this.repository.save(productDb);
            });
  }

  @Override
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
