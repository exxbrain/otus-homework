package com.exxbrain.orderservice.domain.event;

import com.exxbrain.orderservice.domain.model.OrderState;
import lombok.Data;

@Data
public class OrderCanceledEvent {
    private final String orderId;
    private final OrderState state = OrderState.CANCELLED;
}
