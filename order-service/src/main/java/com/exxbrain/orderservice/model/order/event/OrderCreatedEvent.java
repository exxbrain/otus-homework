package com.exxbrain.orderservice.model.order.event;

import com.exxbrain.orderservice.model.order.OrderItems;
import lombok.Data;

@Data
public class OrderCreatedEvent implements Event {
    private final String orderId;
    private final String customerId;
    private final OrderItems orderItems;

    public OrderCreatedEvent(String orderId, String customerId, OrderItems orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
    }
}
