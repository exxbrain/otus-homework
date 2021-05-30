package com.exxbrain.orderservice.model.order.command;

import com.exxbrain.orderservice.model.order.OrderRevision;
import lombok.Data;

@Data
public class ReviseOrderCommand {
    private OrderRevision revision;
}
