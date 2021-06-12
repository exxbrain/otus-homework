package com.exxbrain.orderservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItem {
    @NotNull
    private final String productId;
    @NotNull
    private final String productName;
    @NotNull
    @Positive
    private final BigDecimal price;
    @Positive
    private int quantity;



    public void add(int qntity) {
        quantity = quantity + qntity;
    }

    public BigDecimal total() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}