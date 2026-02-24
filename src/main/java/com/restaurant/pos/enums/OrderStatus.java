package com.restaurant.pos.enums;

/**
 * DESIGN PATTERN: State Pattern (Pattern #16)
 * These states represent the lifecycle of an Order.
 * The Order transitions between these states as it is processed.
 *
 * NEW → SENT → READY → PAID / CANCELLED
 */
public enum OrderStatus {
    NEW,        // Order created, not yet sent to station
    SENT,       // Order sent to kitchen/bar/pizza
    READY,      // All items marked ready by station
    PAID,       // Payment completed
    CANCELLED   // Order voided by admin
}
