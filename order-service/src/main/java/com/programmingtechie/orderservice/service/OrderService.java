package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.dto.InventoryResponse;
import com.programmingtechie.orderservice.dto.OrderRequestDto;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItem;
import com.programmingtechie.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  private final WebClient.Builder webClientBuilder;

  public void placeOrder(OrderRequestDto orderRequestDto) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());
    List<OrderLineItem> collectList =
        orderRequestDto.getOrderLineItemDtoList().stream()
            .map(
                item -> {
                  OrderLineItem orderLineItem = new OrderLineItem();
                  orderLineItem.setId(item.getId());
                  orderLineItem.setSkuCode(item.getSkuCode());
                  orderLineItem.setPrice(item.getPrice());
                  orderLineItem.setQuantity(item.getQuantity());
                  return orderLineItem;
                })
            .collect(Collectors.toList());
    order.setOrderLineItemList(collectList);

    List<String> skuCodes =
        order.getOrderLineItemList().stream()
            .map(OrderLineItem::getSkuCode)
            .collect(Collectors.toList());

    // call Inventory Service, and place order if product is in stock

    InventoryResponse[] inventoryResponsesArray =
            webClientBuilder.build()
            .get()
            .uri(
                "http://inventory-service/api/inventory",
                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
            .retrieve()
            .bodyToMono(InventoryResponse[].class)
            .block();

    assert inventoryResponsesArray != null;
    if (inventoryResponsesArray.length != 0) {
      boolean allProductInStock =
          Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);

      if (allProductInStock) {
        orderRepository.save(order);
      } else {
        throw new IllegalStateException("product is not in stock");
      }
    } else {
      throw new IllegalStateException("product is not found");
    }
  }
}
