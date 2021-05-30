package com.exxbrain.orderservice.model.order.command;

import com.exxbrain.orderservice.model.order.OrderItems;
import lombok.Data;

@Data
public class CreateOrderCommand {
    private final String orderId;
    private final String customerId;
    private final OrderItems orderItems;
}
