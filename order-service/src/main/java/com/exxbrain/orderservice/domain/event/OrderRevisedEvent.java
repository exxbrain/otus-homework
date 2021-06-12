package com.exxbrain.orderservice.domain.event;

import com.exxbrain.orderservice.domain.model.OrderRevision;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRevisedEvent {
    private final String orderId;
    private final OrderRevision orderRevision;
    private final BigDecimal newPrice;
}
