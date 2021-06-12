package com.exxbrain.orderservice.interfaces.rest;

import lombok.Data;

@Data
public class ReviseOrderDto {
    private String productId;
    private int quantity;
}
