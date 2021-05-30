package com.exxbrain.orderservice.model.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderItems {
    private final List<OrderItem> items;

    public void update(OrderRevision orderRevision) {
        items.forEach(
                li -> {
                    Integer revised = orderRevision
                            .getRevisedProductQuantities().get(
                                    li.getProductId());
                    li.setQuantity(revised);
                });
    }
}
