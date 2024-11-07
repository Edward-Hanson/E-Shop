package org.hanson.eddyshop.controller;

import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.dto.response.order.OrderDto;
import org.hanson.eddyshop.service.order.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("place-order")
    @ResponseStatus(CREATED)
    public OrderDto placeOrder(@RequestParam Long userId){
        return orderService.placeOrder(userId);
    }

    @GetMapping("{orderId}")
    @ResponseStatus(OK)
    public OrderDto getOrderById(@PathVariable Long orderId){
        return orderService.getOrder(orderId);
    }

    @GetMapping("{userId}/orders")
    @ResponseStatus(OK)
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId){
        return orderService.getUserOrders(userId);
    }
}
