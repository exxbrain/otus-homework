package com.exxbrain.orderservice.domain.command;

import com.exxbrain.orderservice.domain.model.OrderRevision;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class ReviseOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
    private OrderRevision revision;
}
