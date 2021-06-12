package com.exxbrain.orderservice.domain.event;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
public class OrderItemRemovedEvent {
    @TargetAggregateIdentifier
    private final String orderId;
    private final String productId;
    private final BigDecimal newPrice;
}
