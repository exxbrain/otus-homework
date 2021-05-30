package com.exxbrain.orderservice;

import com.exxbrain.orderservice.model.order.Order;
import com.exxbrain.orderservice.model.order.command.CreateOrderCommand;

class OrderRepository {
    void save(CreateOrderCommand command) {
        var order = new Order();
        var event = order.process(command);
        order.apply(event);
    }
}