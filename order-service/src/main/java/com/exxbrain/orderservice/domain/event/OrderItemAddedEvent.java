package com.exxbrain.orderservice.domain.event;

import com.exxbrain.orderservice.domain.model.OrderItem;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
public class OrderItemAddedEvent {
    @TargetAggregateIdentifier
    private final String orderId;
    private final OrderItem item;
    private final BigDecimal newPrice;
}
