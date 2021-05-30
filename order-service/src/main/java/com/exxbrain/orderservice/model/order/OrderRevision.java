package com.exxbrain.orderservice.model.order;

import lombok.Data;
import java.util.Map;

@Data
public class OrderRevision {
    private final Map<String, Integer> revisedProductQuantities;
}
