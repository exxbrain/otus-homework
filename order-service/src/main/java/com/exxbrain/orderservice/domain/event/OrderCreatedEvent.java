package com.exxbrain.orderservice.domain.event;

import com.exxbrain.orderservice.domain.model.OrderItems;
import com.exxbrain.orderservice.domain.model.OrderState;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderCreatedEvent {
    private final String orderId;
    private final String customerId;
    private final OrderItems orderItems;
    private OrderState state = OrderState.CREATED;

    public OrderCreatedEvent(String orderId, String customerId, OrderItems orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
    }
}
