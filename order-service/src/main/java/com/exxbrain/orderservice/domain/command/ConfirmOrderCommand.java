package com.exxbrain.orderservice.domain.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class ConfirmOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
}
