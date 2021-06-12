package com.exxbrain.orderservice.domain.projections;

import com.exxbrain.orderservice.domain.model.OrderRevision;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "totalIndex", columnList = "total"),
        @Index(name = "createdAtIndex", columnList = "createdAt"),
        @Index(name = "stateCreatedAtIndex", columnList = "state, createdAt"),
        @Index(name = "stateCreatedAtDESCIndex", columnList = "state, createdAt DESC"),
        @Index(name = "stateTotalIndex", columnList = "state, total"),
        @Index(name = "stateTotalDescIndex", columnList = "state, total DESC")
})
@EqualsAndHashCode(exclude = "orderItems")
public class OrderView {
    @Id
    private UUID id;
    @NotNull
    private String state;
    @NotNull
    private String customerId;
    @NotNull
    private Date createdAt;
    private BigDecimal total;
    @OneToMany(mappedBy = "orderView", cascade = CascadeType.ALL)
    private final Set<OrderItemView> orderItems = new HashSet<>();

    public void addItems(Collection<OrderItemView> orderItems) {
        orderItems.forEach(this::addItem);
    }

    public void addItem(OrderItemView orderItemView) {
        orderItemView.setOrderView(this);
        this.orderItems.add(orderItemView);
    }

    public void revise(OrderRevision orderRevision) {
        orderItems.forEach(x -> {
            var quantity = orderRevision.getRevisedProductQuantities().get(x.getProductId());
            if (quantity == null) {
                return;
            }
            x.setQuantity(quantity);
        });
    }
}
