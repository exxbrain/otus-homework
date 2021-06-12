package com.exxbrain.orderservice.domain.command;

import com.exxbrain.orderservice.domain.model.OrderItem;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class AddOrderItemCommand {
    @TargetAggregateIdentifier
    private final String orderId;
    private final OrderItem item;
}
