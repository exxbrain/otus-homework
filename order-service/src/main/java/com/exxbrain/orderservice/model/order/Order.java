package com.exxbrain.orderservice.model.order;

import com.exxbrain.orderservice.model.order.command.CancelOrderCommand;
import com.exxbrain.orderservice.model.order.command.CreateOrderCommand;
import com.exxbrain.orderservice.model.order.command.ReviseOrderCommand;
import com.exxbrain.orderservice.model.order.command.SendOrderForPaymentCommand;
import com.exxbrain.orderservice.model.order.event.*;

public class Order extends Aggregate {
    private String id;
    private OrderState state;
    private String customerId;
    private OrderItems items;


    public OrderCreatedEvent process(CreateOrderCommand command) {
        return new OrderCreatedEvent(
                        command.getOrderId(),
                        command.getCustomerId(),
                        command.getOrderItems()
        );
    }

    public void apply(OrderCreatedEvent event) {
        items = event.getOrderItems();
        state = OrderState.CREATED;
        id = event.getOrderId();
        customerId = event.getCustomerId();
    }

    public Event process(ReviseOrderCommand command)  {
        if (state == OrderState.CANCELLED || state == OrderState.SENT_FOR_PAYMENT) {
            throw new InvalidOrderStateException("Can't update order items");
        }
        var revision = command.getRevision();
        return new OrderRevised(revision);
    }

    public void apply(OrderRevised event) {
        var orderRevision = event.getOrderRevision();
        if (!orderRevision.getRevisedProductQuantities().isEmpty()) {
            items.update(orderRevision);
        }
    }

    public OrderCanceledEvent process(CancelOrderCommand command) {
        switch (state) {
            case CANCELLED:
                throw new InvalidOrderStateException("Already cancelled");
            case SENT_FOR_PAYMENT:
                throw new InvalidOrderStateException("Already sent for payment");
        }
        return new OrderCanceledEvent();
    }

    public void apply(OrderCanceledEvent event) {
        state = OrderState.CANCELLED;
    }

    public OrderSentForPaymentEvent process(SendOrderForPaymentCommand command) {
        switch (state) {
            case CANCELLED:
                throw new InvalidOrderStateException("Already cancelled");
            case SENT_FOR_PAYMENT:
                throw new InvalidOrderStateException("Already sent for payment");
        }
        return new OrderSentForPaymentEvent();
    }

    public void apply(SendOrderForPaymentCommand event) {
        state = OrderState.SENT_FOR_PAYMENT;
    }

    public static class InvalidOrderStateException extends RuntimeException {
        public InvalidOrderStateException(String msg) {
            super(msg);
        }
    }
}