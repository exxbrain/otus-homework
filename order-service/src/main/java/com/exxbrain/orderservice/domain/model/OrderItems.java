package com.exxbrain.orderservice.domain.model;

import lombok.Getter;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
public class OrderItems {
    private final Map<String, OrderItem> items;

    public OrderItems() {
        this(new HashMap<>());
    }

    private OrderItems(Map<String, OrderItem> items) {
        this.items = items;
    }

    public void update(OrderRevision orderRevision) {
        var newQuantities = orderRevision.getRevisedProductQuantities();
        newQuantities.keySet().forEach(x -> {
            items.get(x).setQuantity(newQuantities.get(x));
        });
    }

    public void addItem(OrderItem item) {
        items.put(item.getProductId(), item);
    }

    public void remove(String productId) {
        items.remove(productId);
    }

    public void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        items.values().forEach(validator::validate);
    }

    public void validate(OrderItem item) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        validator.validate(item);
    }

    public ChangeProposal proposeRevision(OrderRevision revision) {
        var newQuantities = revision.getRevisedProductQuantities();
        newQuantities.keySet().forEach(x -> {
            if (!items.containsKey(x)) {
                throw  new ItemNotFoundException(x);
            }
        });
        var newItems = copy();
        newItems.update(revision);
        return new ChangeProposal(newItems.total());
    }

    public boolean contains(String productId) {
        return items.containsKey(productId);
    }

    public void add(OrderItem orderItem) {
        if (this.contains(orderItem.getProductId())) {
            items
                    .get(orderItem.getProductId())
                    .add(orderItem.getQuantity());
        } else {
            items.put(orderItem.getProductId(), orderItem);
        }
    }

    public BigDecimal total() {
        return items.values().stream()
                .map(OrderItem::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public ChangeProposal proposeAddItem(OrderItem item) {
        validate(item);
        var newItems = copy();
        newItems.add(item);
        return new ChangeProposal(newItems.total());
    }

    public ChangeProposal proposeRemoveItem(String productId) {
        if (!items.containsKey(productId)) {
            throw new ItemNotFoundException(productId);
        }
        var newItems = copy();
        newItems.remove(productId);
        return new ChangeProposal(newItems.total());
    }

    private OrderItems copy() {
        return new OrderItems(new HashMap<>(items));
    }

    public static class ItemNotFoundException extends RuntimeException {
        public ItemNotFoundException(String msg) {
            super(msg);
        }
    }
}
