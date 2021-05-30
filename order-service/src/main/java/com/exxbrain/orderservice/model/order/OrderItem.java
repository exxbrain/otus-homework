package com.exxbrain.orderservice.model.order;

import lombok.Data;

@Data
public class OrderItem {
    private final String productId;
    private final String productName;
    private int quantity;
}