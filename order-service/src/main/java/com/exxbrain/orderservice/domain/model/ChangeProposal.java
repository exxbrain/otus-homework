package com.exxbrain.orderservice.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChangeProposal {
    private final BigDecimal newPrice;
}
