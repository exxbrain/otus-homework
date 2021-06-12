package com.exxbrain.orderservice.domain.model;

import com.exxbrain.orderservice.domain.command.*;
import com.exxbrain.orderservice.domain.event.*;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Aggregate
@Data
public class Order {
    @AggregateIdentifier
    private String orderId;
    private OrderState state;
    private String customerId;
    private OrderItems items;

    protected Order() { }

    @CommandHandler
    public Order(CreateOrderCommand command) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        validator.validate(command);
        command.getOrderItems().validate();
        AggregateLifecycle.apply(new OrderCreatedEvent(command.getOrderId(),
                command.getCustomerId(), command.getOrderItems()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        items = event.getOrderItems();
        state = OrderState.CREATED;
        orderId = event.getOrderId();
        customerId = event.getCustomerId();
    }

    @CommandHandler
    public void handle(AddOrderItemCommand command)  {
        if (state == OrderState.CANCELLED || state == OrderState.CONFIRMED) {
            throw new InvalidOrderStateException("Can't update order items");
        }
        var proposal = items.proposeAddItem(command.getItem());
        AggregateLifecycle.apply(new OrderItemAddedEvent(orderId, command.getItem(), proposal.getNewPrice()));
    }

    @EventSourcingHandler
    public void on(OrderItemAddedEvent event)  {
        items.addItem(event.getItem());
    }

    @CommandHandler
    public void handle(RemoveOrderItemCommand command)  {
        if (state == OrderState.CANCELLED || state == OrderState.CONFIRMED) {
            throw new InvalidOrderStateException("Can't update order items");
        }
        var proposal = items.proposeRemoveItem(command.getProductId());
        AggregateLifecycle.apply(new OrderItemRemovedEvent(orderId, command.getProductId(), proposal.getNewPrice()));
    }

    @EventSourcingHandler
    public void on(OrderItemRemovedEvent event)  {
        items.remove(event.getProductId());
    }


    @CommandHandler
    public void handle(ReviseOrderCommand command)  {
        if (state == OrderState.CANCELLED || state == OrderState.CONFIRMED) {
            throw new InvalidOrderStateException("Can't update order items");
        }
        var proposal = items.proposeRevision(command.getRevision());
        AggregateLifecycle.apply(new OrderRevisedEvent(orderId, command.getRevision(), proposal.getNewPrice()));
    }

    @EventSourcingHandler
    public void on(OrderRevisedEvent event) {
        var orderRevision = event.getOrderRevision();
        if (!orderRevision.getRevisedProductQuantities().isEmpty()) {
            items.update(orderRevision);
        }
    }

    @CommandHandler
    public void handle(CancelOrderCommand command) {
        switch (state) {
            case CANCELLED:
                throw new InvalidOrderStateException("Already cancelled");
            case CONFIRMED:
                throw new InvalidOrderStateException("Already confirmed");
        }
        AggregateLifecycle.apply(new OrderCanceledEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderCanceledEvent event) {
        state = OrderState.CANCELLED;
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        switch (state) {
            case CANCELLED:
                throw new InvalidOrderStateException("Already cancelled");
            case CONFIRMED:
                throw new InvalidOrderStateException("Already confirmed");
        }
        AggregateLifecycle.apply(new OrderConfirmedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        state = OrderState.CONFIRMED;
    }

    public static class InvalidOrderStateException extends RuntimeException {
        public InvalidOrderStateException(String msg) {
            super(msg);
        }
    }
}