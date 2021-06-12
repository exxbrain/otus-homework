package com.exxbrain.orderservice.domain.projections;

import com.exxbrain.orderservice.domain.queries.OrderListQuery;
import com.exxbrain.orderservice.domain.queries.OrderQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderViewProjection {
    private final OrderViewRepository orderViewRepository;
    public OrderViewProjection(OrderViewRepository orderViewRepository) {
        this.orderViewRepository = orderViewRepository;
    }

    @QueryHandler
    public Iterable<OrderView> handle(OrderListQuery query) {
        log.info("Handling OrderListQuery: {}", query);
        if (query.getState() != null) {
            return orderViewRepository.findAllByState(query.getState(), query.getPageable());
        }
        return orderViewRepository.findAll(query.getPageable());
    }

    @QueryHandler
    public OrderView handle(OrderQuery query) {
        log.info("Handling BookQuery: {}", query);
        var book = orderViewRepository.findById(query.getOrderId());
        if (book.isEmpty())
            throw new IllegalArgumentException("Order not found");
        return book.get();
    }
}
