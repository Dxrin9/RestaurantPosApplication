package com.restaurant.pos.patterns.factory;

import com.restaurant.pos.entity.OrderItem;

/**
 * ============================================================
 * DESIGN PATTERN #2: FACTORY METHOD
 * ============================================================
 * Defines a common interface for printing station tickets.
 * Each station (Kitchen, Bar, Pizza) has its own concrete printer.
 * The TicketPrinterFactory creates the correct printer based on station type.
 *
 * This allows us to change how a ticket is formatted per station
 * without modifying the calling code.
 */
public interface TicketPrinter {

    /**
     * Format and "print" (return as String) a ticket for an order item.
     * In a real system, this would send to a receipt printer.
     */
    String printTicket(OrderItem item);
}
