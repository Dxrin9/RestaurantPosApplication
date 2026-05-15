package com.restaurant.pos.patterns.chain;

import com.restaurant.pos.entity.Order;

/** Validates that the order has at least one non-voided item */
public class ItemsValidator extends OrderValidator {
    @Override
    public String validate(Order order) {
        long activeItems = order.getItems().stream()
                .filter(i -> !i.isVoided())
                .count();
        if (activeItems == 0) {
            return "Order must have at least one item.";
        }
        return passToNext(order);
    }
}
