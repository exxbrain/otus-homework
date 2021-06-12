package com.exxbrain.orderservice.domain.projections;

import com.exxbrain.orderservice.domain.model.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface OrderViewRepository extends PagingAndSortingRepository<OrderView, UUID> {
    Page<OrderView> findAllByState(String state, Pageable pageable);
}
