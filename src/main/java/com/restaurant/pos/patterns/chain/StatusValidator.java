package com.restaurant.pos.patterns.chain;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.enums.OrderStatus;

/** Validates order is in correct status to be sent to station */
public class StatusValidator extends OrderValidator {
    @Override
    public String validate(Order order) {
        if (order.getStatus() == OrderStatus.PAID || order.getStatus() == OrderStatus.CANCELLED) {
            return "Cannot send an order that is already " + order.getStatus() + ".";
        }
        return passToNext(order);
    }
}
