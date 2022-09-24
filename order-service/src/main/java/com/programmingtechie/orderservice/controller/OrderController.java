package com.programmingtechie.orderservice.controller;

import com.programmingtechie.orderservice.dto.OrderRequestDto;
import com.programmingtechie.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequestDto orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }
}
