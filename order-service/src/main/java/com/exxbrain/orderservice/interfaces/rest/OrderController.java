package com.exxbrain.orderservice.interfaces.rest;

import com.exxbrain.orderservice.domain.command.*;
import com.exxbrain.orderservice.domain.model.OrderItem;
import com.exxbrain.orderservice.domain.model.OrderItems;
import com.exxbrain.orderservice.domain.model.OrderRevision;
import com.exxbrain.orderservice.domain.projections.OrderView;
import com.exxbrain.orderservice.domain.queries.OrderListQuery;
import com.exxbrain.orderservice.domain.queries.OrderQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public OrderController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody CreateOrderDto dto, JwtAuthenticationToken token) {
        commandGateway.sendAndWait(
                new CreateOrderCommand(dto.getId(), token.getToken().getSubject(), orderItems(dto))
        );
    }

    @PutMapping("/{orderId}/addItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addOrderItem(@PathVariable String orderId, @RequestBody AddOrderItemDto dto) {
        commandGateway.sendAndWait(
                new AddOrderItemCommand(orderId, orderItem(dto.getOrderItemDto()))
        );
    }

    @PutMapping("/{orderId}/removeItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addOrderItem(@PathVariable String orderId, @RequestBody RemoveOrderItemDto dto) {
        commandGateway.sendAndWait(
                new RemoveOrderItemCommand(orderId, dto.getProductId())
        );
    }

    @PutMapping("/{orderId}/revise")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reviseOrder(@PathVariable String orderId, @RequestBody ReviseOrderDto dto) {
        commandGateway.sendAndWait(
                new ReviseOrderCommand(orderId, new OrderRevision(Map.of(dto.getProductId(), dto.getQuantity())))
        );
    }

    @PutMapping("/{orderId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable String orderId) {
        commandGateway.sendAndWait(
                new CancelOrderCommand(orderId)
        );
    }

    @PutMapping("/{orderId}/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmOrder(@PathVariable String orderId) {
        commandGateway.sendAndWait(
                new ConfirmOrderCommand(orderId)
        );
    }

    @GetMapping("/{orderId}")
    public Future<OrderView> getOrder(@PathVariable UUID orderId) {
        return queryGateway.query(new OrderQuery(orderId), OrderView.class);
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public Future<List<OrderView>> getOrders(@RequestParam(required = false) String state,
                                             @PageableDefault(size = 50,
                                                     sort = "createdAt",
                                                     direction = Sort.Direction.DESC) Pageable pageable) {
        return queryGateway.query(new OrderListQuery(pageable, state),
                ResponseTypes.multipleInstancesOf(OrderView.class));
    }

    private OrderItems orderItems(CreateOrderDto dto) {
        var items = new OrderItems();
        dto.getItems().stream().map(this::orderItem).forEach(items::add);
        return items;
    }

    private OrderItem orderItem(OrderItemDto x) {
        return new OrderItem(x.getId().toString(), x.getName(), new BigDecimal(x.getPrice()), 1);
    }
}
