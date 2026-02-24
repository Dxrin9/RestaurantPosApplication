package com.restaurant.pos.patterns.factory;

import com.restaurant.pos.entity.OrderItem;

/** Kitchen station ticket format */
public class KitchenTicketPrinter implements TicketPrinter {
    @Override
    public String printTicket(OrderItem item) {
        return String.format(
            "[KITCHEN TICKET] Table %d | Item: %s x%d | Notes: %s",
            item.getOrder().getTable().getTableNumber(),
            item.getMenuItem().getName(),
            item.getQuantity(),
            item.getNotes() != null ? item.getNotes() : "—"
        );
    }
}
