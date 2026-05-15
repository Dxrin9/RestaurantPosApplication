package com.restaurant.pos.patterns.chain;

import com.restaurant.pos.entity.Order;

/** Validates that the order has a valid table assigned */
public class TableValidator extends OrderValidator {
    @Override
    public String validate(Order order) {
        if (order.getTable() == null) {
            return "Order must have a table assigned.";
        }
        return passToNext(order);
    }
}
