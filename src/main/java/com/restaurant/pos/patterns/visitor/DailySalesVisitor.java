package com.restaurant.pos.patterns.visitor;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.Payment;
import com.restaurant.pos.entity.Shift;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Concrete Visitor: accumulates daily sales data.
 */
@Getter
public class DailySalesVisitor implements ReportVisitor {

    private BigDecimal totalSales     = BigDecimal.ZERO;
    private BigDecimal totalDiscounts = BigDecimal.ZERO;
    private int        totalOrders    = 0;
    private int        totalMinutes   = 0;

    @Override
    public void visitOrder(Order order) {
        totalOrders++;
    }

    @Override
    public void visitPayment(Payment payment) {
        totalSales     = totalSales.add(payment.getFinalAmount());
        totalDiscounts = totalDiscounts.add(
                payment.getDiscountAmount() != null ? payment.getDiscountAmount() : BigDecimal.ZERO
        );
    }

    @Override
    public void visitShift(Shift shift) {
        if (shift.getWorkedMinutes() != null) {
            totalMinutes += shift.getWorkedMinutes();
        }
    }

    public String getSummary() {
        return String.format(
            "Daily Report — Orders: %d | Total Sales: %.2f | Discounts: %.2f | Worked Hours: %.1f",
            totalOrders, totalSales, totalDiscounts, totalMinutes / 60.0
        );
    }
}
