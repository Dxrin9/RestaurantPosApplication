package com.restaurant.pos.patterns.factory;

import com.restaurant.pos.entity.OrderItem;

/** Bar station ticket format */
public class BarTicketPrinter implements TicketPrinter {
    @Override
    public String printTicket(OrderItem item) {
        return String.format(
            "[BAR TICKET] Table %d | Drink: %s x%d | Notes: %s",
            item.getOrder().getTable().getTableNumber(),
            item.getMenuItem().getName(),
            item.getQuantity(),
            item.getNotes() != null ? item.getNotes() : "—"
        );
    }
}
