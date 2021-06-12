package com.exxbrain.orderservice.interfaces.rest;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {
    private String id;
    private List<OrderItemDto> items;
}
