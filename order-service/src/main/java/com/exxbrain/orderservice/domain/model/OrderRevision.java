package com.exxbrain.orderservice.domain.model;

import lombok.Data;
import java.util.Map;

@Data
public class OrderRevision {
    private final Map<String, Integer> revisedProductQuantities;
}
