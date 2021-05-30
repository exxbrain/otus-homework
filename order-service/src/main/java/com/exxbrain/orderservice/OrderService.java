package com.exxbrain.orderservice;

import com.exxbrain.orderservice.model.order.OrderItem;
import com.exxbrain.orderservice.model.order.OrderItems;
import com.exxbrain.orderservice.model.order.command.CreateOrderCommand;

import java.util.Collections;

public class OrderService {

    private final OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    void createOrder(String orderId, String customerId, OrderItem item) {
        var items = new OrderItems(Collections.singletonList(item));
        orderRepository.save(new CreateOrderCommand(orderId, customerId, items));
    }
}