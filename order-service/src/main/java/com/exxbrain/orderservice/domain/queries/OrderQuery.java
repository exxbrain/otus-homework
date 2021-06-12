package com.exxbrain.orderservice.domain.queries;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderQuery {
    private final UUID orderId;
}
