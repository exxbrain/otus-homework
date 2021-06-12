package com.exxbrain.orderservice.domain.projections;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@ToString
public class OrderItemView {
    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    @Getter
    @Setter
    private String productId;
    @NotNull
    @Getter
    @Setter
    private String productName;
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Setter
    private OrderView orderView;
    @Getter
    @Setter
    private int quantity;
    @Getter
    @Setter
    private BigDecimal price;

    public OrderItemView(String productId, String productName, int quantity, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    protected OrderItemView() {};
}
