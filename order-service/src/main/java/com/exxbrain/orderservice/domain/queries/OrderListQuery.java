package com.exxbrain.orderservice.domain.queries;

import com.exxbrain.orderservice.domain.model.OrderState;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class OrderListQuery {
    private final Pageable pageable;
    private final String state;
}
