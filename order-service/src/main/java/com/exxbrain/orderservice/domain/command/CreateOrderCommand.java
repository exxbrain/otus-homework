package com.exxbrain.orderservice.domain.command;

import com.exxbrain.orderservice.domain.model.OrderItems;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Data
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    @NotNull
    private final String orderId;
    @NotNull
    private final String customerId;
    private final OrderItems orderItems;
}
