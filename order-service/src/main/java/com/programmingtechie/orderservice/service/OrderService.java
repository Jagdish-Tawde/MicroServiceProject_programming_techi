package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.dto.OrderRequestDto;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItem;
import com.programmingtechie.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> collectList =
                orderRequestDto.getOrderLineItemDtoList().stream().map(item -> {
                            OrderLineItem orderLineItem = new OrderLineItem();
                            orderLineItem.setId(item.getId());
                            orderLineItem.setSkuCode(item.getSkuCode());
                            orderLineItem.setPrice(item.getPrice());
                            orderLineItem.setQuantity(item.getQuantity());
                            return orderLineItem;
                        }
                ).collect(Collectors.toList());
        order.setOrderLineItemList(collectList);
        orderRepository.save(order);
    }
}
