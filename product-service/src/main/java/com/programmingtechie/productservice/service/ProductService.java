package com.programmingtechie.productservice.service;

import com.programmingtechie.productservice.dto.ProductRequest;
import com.programmingtechie.productservice.dto.ProductResponse;
import com.programmingtechie.productservice.model.Product;
import com.programmingtechie.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;

  public void createProduct(ProductRequest productRequest) {
    Product product =
        Product.builder()
            .name(productRequest.getName())
            .description(productRequest.getDescription())
            .price(productRequest.getPrice())
            .build();
    productRepository.save(product);
    log.info("product {} is saved", product.getId());
  }

  public List<ProductResponse> getAllProduct() {
    List<Product> allProduct = productRepository.findAll();
    return allProduct.stream()
        .map(
            one -> ProductResponse.builder()
                .name(one.getName())
                .description(one.getDescription())
                .price(one.getPrice())
                .build())
        .collect(Collectors.toList());
  }
}
