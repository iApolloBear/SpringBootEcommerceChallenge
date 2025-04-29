package com.aldo.ecommerce_challenge.orderItems.services;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.exceptions.OrderItemNotFoundException;
import com.aldo.ecommerce_challenge.orderItems.mappers.OrderItemMapper;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orderItems.repositories.OrderItemRepository;
import com.aldo.ecommerce_challenge.orders.exceptions.OrderNotFoundException;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import com.aldo.ecommerce_challenge.products.exceptions.ProductNotFoundException;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class OrderItemServiceImpl implements OrderItemService {
  private final OrderItemRepository orderItemRepository;
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final OrderItemMapper orderItemMapper;

  public OrderItemServiceImpl(
      OrderItemRepository orderItemRepository,
      ProductRepository productRepository,
      OrderRepository orderRepository,
      OrderItemMapper orderItemMapper) {
    this.orderItemRepository = orderItemRepository;
    this.productRepository = productRepository;
    this.orderRepository = orderRepository;
    this.orderItemMapper = orderItemMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderItemDTO> findAll() {
    return StreamSupport.stream(this.orderItemRepository.findAll().spliterator(), false)
        .map(this.orderItemMapper::toOrderItemDto)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<OrderItemDTO> findById(Long id) {
    return this.orderItemRepository.findById(id).map(this.orderItemMapper::toOrderItemDto);
  }

  @Override
  @Transactional
  public OrderItemDTO save(OrderItemCreateDTO orderItemDto) {
    Product product =
        this.productRepository
            .findById(orderItemDto.getProductId())
            .orElseThrow(() -> new ProductNotFoundException(orderItemDto.getProductId()));
    Order order =
        this.orderRepository
            .findById(orderItemDto.getOrderId())
            .orElseThrow(() -> new OrderItemNotFoundException(orderItemDto.getOrderId()));
    OrderItem orderItem = new OrderItem(order, product, orderItemDto.getQuantity());
    order.addItem(orderItem);
    this.orderRepository.save(order);
    return this.orderItemMapper.toOrderItemDto(this.orderItemRepository.save(orderItem));
  }

  @Override
  @Transactional
  public Optional<OrderItemDTO> update(Long id, OrderItemUpdateDTO orderItemDto) {
    return this.orderItemRepository
        .findById(id)
        .map(
            orderItemDb -> {
              if (orderItemDto.getOrderId() != null) {
                Order oldOrder = orderItemDb.getOrder();
                oldOrder.setTotal(oldOrder.getTotal().subtract(orderItemDb.getPrice()));
                Order newOrder =
                    this.orderRepository
                        .findById(orderItemDto.getOrderId())
                        .orElseThrow(() -> new OrderNotFoundException(orderItemDto.getOrderId()));
                newOrder.setTotal(newOrder.getTotal().add(orderItemDb.getPrice()));
                this.orderRepository.saveAll(List.of(oldOrder, newOrder));
                orderItemDb.setOrder(newOrder);
              }

              if (orderItemDto.getProductId() != null) {
                Order order = orderItemDb.getOrder();
                order.setTotal(order.getTotal().subtract(orderItemDb.getPrice()));
                Product product =
                    this.productRepository
                        .findById(orderItemDto.getProductId())
                        .orElseThrow(
                            () -> new ProductNotFoundException(orderItemDto.getProductId()));
                orderItemDb.setProduct(product);
                orderItemDb.setPrice(
                    product.getPrice().multiply(BigDecimal.valueOf(orderItemDb.getQuantity())));
                order.setTotal(order.getTotal().add(orderItemDb.getPrice()));
                this.orderRepository.save(order);
              }

              if (orderItemDto.getQuantity() != null) {
                Order order = orderItemDb.getOrder();

                if (orderItemDto.getQuantity() <= 0) {
                  this.orderItemRepository.delete(orderItemDb);
                  OrderItemDTO result = this.orderItemMapper.toOrderItemDto(orderItemDb);
                  order.removeItem(orderItemDb);
                  this.orderRepository.save(order);
                  return result;
                }

                order.setTotal(order.getTotal().subtract(orderItemDb.getPrice()));
                orderItemDb.setQuantity(orderItemDto.getQuantity());
                orderItemDb.setPrice(
                    orderItemDb
                        .getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(orderItemDb.getQuantity())));
                order.setTotal(order.getTotal().add(orderItemDb.getPrice()));
                this.orderRepository.save(order);
              }

              OrderItem updatedOrderItem = this.orderItemRepository.save(orderItemDb);
              return this.orderItemMapper.toOrderItemDto(updatedOrderItem);
            });
  }

  @Override
  @Transactional
  public Optional<OrderItemDTO> delete(Long id) {
    return this.orderItemRepository
        .findById(id)
        .map(
            orderItem -> {
              Order order =
                  this.orderRepository.findById(orderItem.getOrder().getId()).orElseThrow();
              order.removeItem(orderItem);
              this.orderRepository.save(order);
              this.orderItemRepository.delete(orderItem);
              return this.orderItemMapper.toOrderItemDto(orderItem);
            });
  }
}
