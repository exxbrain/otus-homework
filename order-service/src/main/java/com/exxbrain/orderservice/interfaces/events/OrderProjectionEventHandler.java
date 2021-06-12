package com.exxbrain.orderservice.interfaces.events;

import com.exxbrain.orderservice.domain.event.*;
import com.exxbrain.orderservice.domain.model.OrderItem;
import com.exxbrain.orderservice.domain.model.OrderItems;
import com.exxbrain.orderservice.domain.projections.OrderItemView;
import com.exxbrain.orderservice.domain.projections.OrderItemViewRepository;
import com.exxbrain.orderservice.domain.projections.OrderView;
import com.exxbrain.orderservice.domain.projections.OrderViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional
public class OrderProjectionEventHandler {
    private final OrderViewRepository orderViewRepository;
    private final OrderItemViewRepository orderItemViewRepository;
    public OrderProjectionEventHandler(OrderViewRepository orderViewRepository,
                                       OrderItemViewRepository orderItemViewRepository) {
        this.orderViewRepository = orderViewRepository;
        this.orderItemViewRepository = orderItemViewRepository;
    }

    @EventHandler
    public void handle(OrderCreatedEvent event) {
        log.info("Applying OrderCreatedEvent: {}", event);
        var orderView = new OrderView(UUID.fromString(event.getOrderId()), event.getState().name(), event.getCustomerId(),
                new Date(), event.getOrderItems().total());
        orderView.addItems(orderItems(event.getOrderItems()));
        this.orderViewRepository.save(orderView);
    }

    @EventHandler
    public void handle(OrderItemAddedEvent event) {
        log.info("Applying OrderCreatedEvent: {}", event);
        var orderView = orderView(event.getOrderId());
        orderView.setTotal(event.getNewPrice());
        orderView.addItem(orderItemView(event.getItem()));
        this.orderViewRepository.save(orderView);
    }

    @EventHandler
    public void handle(OrderItemRemovedEvent event) {
        log.info("Applying OrderCreatedEvent: {}", event);
        var orderView = orderView(event.getOrderId());
        orderView.setTotal(event.getNewPrice());
        this.orderItemViewRepository.removeByOrderViewAndProductId(orderView, event.getProductId());
        this.orderViewRepository.save(orderView);
    }

    @EventHandler
    public void handle(OrderRevisedEvent event) {
        log.info("Applying OrderCreatedEvent: {}", event);
        var orderView = orderView(event.getOrderId());
        orderView.setTotal(event.getNewPrice());
        orderView.revise(event.getOrderRevision());
        this.orderViewRepository.save(orderView);
    }

    @EventHandler
    public void handle(OrderCanceledEvent event) {
        log.info("Applying OrderCreatedEvent: {}", event);
        var orderView = orderView(event.getOrderId());
        orderView.setState(event.getState().name());
        this.orderViewRepository.save(orderView);
    }

    @EventHandler
    public void handle(OrderConfirmedEvent event) {
        log.info("Applying OrderCreatedEvent: {}", event);
        var orderView = orderView(event.getOrderId());
        orderView.setState(event.getState().name());
        this.orderViewRepository.save(orderView);
    }

    private OrderItemView orderItemView(OrderItem orderItem) {
        return new OrderItemView(orderItem.getProductId(),
                orderItem.getProductName(),
                orderItem.getQuantity(),
                orderItem.getPrice());
    }

    private Set<OrderItemView> orderItems(OrderItems orderItems) {
        return orderItems.getItems().values().stream().map(this::orderItemView)
                .collect(Collectors.toSet());
    }

    private OrderView orderView(String orderId) {
        var uuid = UUID.fromString(orderId);
        return this.orderViewRepository.findById(uuid)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String msg) {
            super(msg);
        }
    }
}
