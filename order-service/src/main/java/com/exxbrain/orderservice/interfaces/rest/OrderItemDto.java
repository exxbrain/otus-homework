package com.exxbrain.orderservice.interfaces.rest;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemDto {
    private UUID id;
    private String name;
    private String price;
}
