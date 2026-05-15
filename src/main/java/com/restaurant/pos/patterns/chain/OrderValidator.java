package com.restaurant.pos.patterns.chain;

import com.restaurant.pos.entity.Order;

/**
 * ============================================================
 * DESIGN PATTERN #6: CHAIN OF RESPONSIBILITY
 * ============================================================
 * Order validation is split into a chain of validators.
 * Each handler either handles the request or passes it to the next.
 *
 * Chain: TableValidator → ItemsValidator → StatusValidator → (end)
 *
 * If any validator fails, the chain stops and returns an error.
 */
public abstract class OrderValidator {

    protected OrderValidator next;

    public OrderValidator setNext(OrderValidator next) {
        this.next = next;
        return next;
    }

    /**
     * Validate the order. Return null if valid, or an error message string.
     */
    public abstract String validate(Order order);

    /** Helper: pass to next in chain, or return null (valid) */
    protected String passToNext(Order order) {
        if (next != null) {
            return next.validate(order);
        }
        return null; // No more handlers → valid
    }
}
