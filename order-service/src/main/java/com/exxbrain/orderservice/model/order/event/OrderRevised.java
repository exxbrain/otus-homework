package com.exxbrain.orderservice.model.order.event;

import com.exxbrain.orderservice.model.order.OrderRevision;
import lombok.Data;

@Data
public class OrderRevised implements Event {
    private final OrderRevision orderRevision;
}
