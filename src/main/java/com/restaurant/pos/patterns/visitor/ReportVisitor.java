package com.restaurant.pos.patterns.visitor;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.Payment;
import com.restaurant.pos.entity.Shift;

/**
 * ============================================================
 * DESIGN PATTERN #21: VISITOR
 * ============================================================
 * Allows adding new operations (reports) to entities without modifying them.
 * Each visitor implements a different type of report.
 *
 * Element: Order, Payment, Shift (accept a visitor)
 * Visitor: ReportVisitor interface
 * Concrete Visitors: DailySalesVisitor, VoidItemsVisitor
 *
 * This avoids polluting the entity classes with reporting logic.
 */
public interface ReportVisitor {
    void visitOrder(Order order);
    void visitPayment(Payment payment);
    void visitShift(Shift shift);
}
