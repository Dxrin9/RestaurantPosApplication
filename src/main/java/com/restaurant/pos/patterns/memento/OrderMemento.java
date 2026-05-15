package com.restaurant.pos.patterns.memento;

import com.restaurant.pos.enums.OrderStatus;

import java.util.List;

/**
 * ============================================================
 * DESIGN PATTERN #14: MEMENTO
 * ============================================================
 * Captures and restores an order's internal state without violating encapsulation.
 * Used when a waiter wants to undo changes to an order before sending.
 *
 * Originator: Order (in service layer)
 * Memento:    OrderMemento (snapshot of order state)
 * Caretaker:  OrderHistory (stack of mementos)
 */
public class OrderMemento {

    private final OrderStatus status;
    private final List<Long> itemIds;  // IDs of items at time of snapshot
    private final String notes;

    public OrderMemento(OrderStatus status, List<Long> itemIds, String notes) {
        this.status  = status;
        this.itemIds = itemIds;
        this.notes   = notes;
    }

    public OrderStatus getStatus()   { return status; }
    public List<Long> getItemIds()   { return itemIds; }
    public String getNotes()         { return notes; }
}
