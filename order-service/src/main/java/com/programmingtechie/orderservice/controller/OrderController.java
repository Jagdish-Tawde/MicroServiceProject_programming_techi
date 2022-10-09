package com.programmingtechie.orderservice.controller;

import com.programmingtechie.orderservice.dto.OrderRequestDto;
import com.programmingtechie.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
  @TimeLimiter(name = "inventory")
  @Retry(name = "inventory")
  public CompletableFuture<String> placeOrder(@RequestBody OrderRequestDto orderRequest) {
    return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
  }

  public CompletableFuture<String> fallBackMethod(
      OrderRequestDto orderRequestDto, RuntimeException runtimeException) {
    return CompletableFuture.supplyAsync(
        () -> "oops something went wrong! Please wait for some time");
  }
}
