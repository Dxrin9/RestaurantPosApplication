package com.restaurant.pos.patterns.builder;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.OrderItem;
import com.restaurant.pos.entity.RestaurantTable;
import com.restaurant.pos.entity.User;
import com.restaurant.pos.enums.OrderStatus;
import com.restaurant.pos.patterns.singleton.OrderNumberGenerator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * DESIGN PATTERN #10: BUILDER
 * ============================================================
 * Constructs an Order object step by step, allowing optional configuration.
 * Useful when creating orders with many optional fields.
 *
 * Usage:
 *   Order order = orderBuilder
 *       .forTable(table)
 *       .byWaiter(waiter)
 *       .withNotes("Window seat please")
 *       .build(numberGenerator);
 */
@Component
public class OrderBuilder {

    private RestaurantTable table;
    private User waiter;
    private String notes;
    private final List<OrderItem> items = new ArrayList<>();

    public OrderBuilder forTable(RestaurantTable table) {
        this.table = table;
        return this;
    }

    public OrderBuilder byWaiter(User waiter) {
        this.waiter = waiter;
        return this;
    }

    public OrderBuilder withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public OrderBuilder withItem(OrderItem item) {
        this.items.add(item);
        return this;
    }

    /** Builds the Order object — call after setting all properties */
    public Order build(OrderNumberGenerator generator) {
        if (table == null) throw new IllegalStateException("Table must be set before building an order.");

        Order order = new Order();
        order.setOrderNumber(generator.next());
        order.setTable(table);
        order.setWaiter(waiter);
        order.setNotes(notes);
        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setItems(new ArrayList<>(items));

        // Reset builder for reuse
        reset();
        return order;
    }

    public void reset() {
        this.table  = null;
        this.waiter = null;
        this.notes  = null;
        this.items.clear();
    }
}
