package com.restaurant.pos.patterns.factory;

import com.restaurant.pos.entity.OrderItem;

/** Pizza station ticket format */
public class PizzaTicketPrinter implements TicketPrinter {
    @Override
    public String printTicket(OrderItem item) {
        return String.format(
            "[PIZZA TICKET] Table %d | Pizza: %s x%d | Extras: %s | Notes: %s",
            item.getOrder().getTable().getTableNumber(),
            item.getMenuItem().getName(),
            item.getQuantity(),
            item.getExtras() != null ? item.getExtras() : "none",
            item.getNotes() != null ? item.getNotes() : "—"
        );
    }
}
