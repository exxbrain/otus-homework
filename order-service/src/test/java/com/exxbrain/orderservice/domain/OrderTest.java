package com.exxbrain.orderservice.domain;

import com.exxbrain.orderservice.domain.command.CancelOrderCommand;
import com.exxbrain.orderservice.domain.command.ConfirmOrderCommand;
import com.exxbrain.orderservice.domain.command.CreateOrderCommand;
import com.exxbrain.orderservice.domain.command.ReviseOrderCommand;
import com.exxbrain.orderservice.domain.event.OrderConfirmedEvent;
import com.exxbrain.orderservice.domain.event.OrderCreatedEvent;
import com.exxbrain.orderservice.domain.event.OrderRevisedEvent;
import com.exxbrain.orderservice.domain.model.Order;
import com.exxbrain.orderservice.domain.model.OrderItem;
import com.exxbrain.orderservice.domain.model.OrderItems;
import com.exxbrain.orderservice.domain.model.OrderRevision;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class OrderTest {
    private FixtureConfiguration<Order> fixture;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(Order.class);
    }

    @Test
    public void test_HandleCreateOrderCommand_Should_ProduceOrderCreateEvent() {
        var createCommand = createOrderCommand();
        var expectedEvent = new OrderCreatedEvent(createCommand.getOrderId(),
                createCommand.getCustomerId(), createCommand.getOrderItems());
        fixture.givenNoPriorActivity()
                .when(createCommand)
                .expectEvents(expectedEvent);
    }

    @Test
    public void test_CancelConfirmedOrder_Should_ThrowException() {
        var orderCreatedEvent = orderCreatedEvent();
        fixture.given(orderCreatedEvent)
                .andGiven(new OrderConfirmedEvent(orderCreatedEvent.getOrderId()))
                .when(new CancelOrderCommand(orderCreatedEvent.getOrderId()))
                .expectException(Order.InvalidOrderStateException.class);
    }

    @Test
    public void test_ReviseCreatedOrderWithNonexistentProduct_Should_ProduceOrderRevisedEventWithNoProducts() {
        var orderCreatedEvent = orderCreatedEvent();
        var productId= UUID.randomUUID().toString();
        var quantity = 1;
        fixture.given(orderCreatedEvent)
                .when(new ReviseOrderCommand(orderCreatedEvent.getOrderId(), new OrderRevision(Map.of(productId, quantity))))
                .expectException(OrderItems.ItemNotFoundException.class);
    }

    @Test
    public void test_ReviseConfirmedOrder_Should_ThrowException() {
        var orderCreatedEvent = orderCreatedEvent();
        var productId= UUID.randomUUID().toString();
        var quantity = 1;
        fixture.given(orderCreatedEvent)
                .andGiven(new OrderConfirmedEvent(orderCreatedEvent.getOrderId()))
                .when(new ReviseOrderCommand(orderCreatedEvent.getOrderId(), new OrderRevision(Map.of(productId, quantity))))
                .expectException(Order.InvalidOrderStateException.class);
    }

    @Test
    public void testReviseOrder_Should_ProduceEventWithCorrectTotalPrice() {
        var product1Id = UUID.randomUUID().toString();
        var product2Id = UUID.randomUUID().toString();
        var items = new OrderItems();
        items.add(new OrderItem(product1Id, "Box", BigDecimal.valueOf(2), 1));
        items.add(new OrderItem(product2Id, "Table", BigDecimal.valueOf(3), 2));
        var orderCreatedEvent = orderCreatedEvent(items);
        fixture.given(orderCreatedEvent)
                .when(new ReviseOrderCommand(orderCreatedEvent.getOrderId(), new OrderRevision(Map.of(product1Id, 4))))
                .expectEvents(new OrderRevisedEvent(orderCreatedEvent.getOrderId(),
                        new OrderRevision(Map.of(product1Id, 4)), BigDecimal.valueOf(14)));
    }

    @Test
    public void testReviseOrder_Should_UpdateProductQuantities() {
        var product1Id = UUID.randomUUID().toString();
        var product2Id = UUID.randomUUID().toString();
        var items = new OrderItems();
        items.add(new OrderItem(product1Id, "Box", BigDecimal.valueOf(2), 1));
        items.add(new OrderItem(product2Id, "Table", BigDecimal.valueOf(3), 2));
        var orderCreatedEvent = orderCreatedEvent(items);
        var orderRevisedEvent = new OrderRevisedEvent(orderCreatedEvent.getOrderId(),
                new OrderRevision(Map.of(product1Id, 4)), BigDecimal.valueOf(14));
        fixture.given(orderCreatedEvent)
                .andGiven(orderRevisedEvent)
                .when(new ConfirmOrderCommand(orderCreatedEvent.getOrderId()))
                .expectState(order -> {
                    Assertions.assertEquals(4, order.getItems().getItems().get(product1Id).getQuantity());
                    Assertions.assertEquals(2, order.getItems().getItems().get(product2Id).getQuantity());
                });
    }

    private OrderItems createOrderItems() {
        var product1Id = UUID.randomUUID().toString();
        var product2Id = UUID.randomUUID().toString();
        var res = new OrderItems();
        res.add(new OrderItem(product1Id, "Box", BigDecimal.valueOf(1), 1));
        res.add(new OrderItem(product2Id, "Table", BigDecimal.valueOf(2), 1));
        return res;
    }

    private CreateOrderCommand createOrderCommand() {
        return createOrderCommand(createOrderItems());
    }

    private CreateOrderCommand createOrderCommand(OrderItems orderItems) {
        var orderId = UUID.randomUUID().toString();
        var customerId = UUID.randomUUID().toString();
        return new CreateOrderCommand(orderId, customerId, orderItems);
    }

    private OrderCreatedEvent orderCreatedEvent() {
        return orderCreatedEvent(createOrderItems());
    }

    private OrderCreatedEvent orderCreatedEvent(OrderItems orderItems) {
        var orderId = UUID.randomUUID().toString();
        var customerId = UUID.randomUUID().toString();
        return new OrderCreatedEvent(orderId, customerId, orderItems);
    }
}