package com.exxbrain.orderservice.domain.projections;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface OrderItemViewRepository extends PagingAndSortingRepository<OrderItemView, UUID> {
    void removeByOrderViewAndProductId(OrderView orderView, String productId);
}
