package com.restaurant.pos.patterns.memento;

import com.restaurant.pos.entity.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

/**
 * CARETAKER: Stores and retrieves mementos for orders.
 * Does not modify or inspect the memento contents.
 */
@Component
public class OrderHistory {

    private final Deque<OrderMemento> history = new ArrayDeque<>();

    /** Save current state of the order */
    public void save(Order order) {
        OrderMemento memento = new OrderMemento(
            order.getStatus(),
            order.getItems().stream().map(i -> i.getId()).collect(Collectors.toList()),
            order.getNotes()
        );
        history.push(memento);
        System.out.println("[MEMENTO] Order state saved. Stack depth: " + history.size());
    }

    /** Restore last saved state */
    public OrderMemento restore() {
        if (history.isEmpty()) {
            System.out.println("[MEMENTO] Nothing to restore.");
            return null;
        }
        OrderMemento memento = history.pop();
        System.out.println("[MEMENTO] Restored previous state.");
        return memento;
    }

    public boolean hasHistory() {
        return !history.isEmpty();
    }
}
