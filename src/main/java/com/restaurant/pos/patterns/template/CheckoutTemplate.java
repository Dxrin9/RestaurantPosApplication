package com.restaurant.pos.patterns.template;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.Payment;
import com.restaurant.pos.entity.User;
import com.restaurant.pos.enums.PaymentMethod;

import java.math.BigDecimal;

/**
 * ============================================================
 * DESIGN PATTERN #20: TEMPLATE METHOD
 * ============================================================
 * Defines the SKELETON of the checkout algorithm in a base class.
 * Subclasses override specific steps without changing the overall flow.
 *
 * Checkout flow:
 *   1. validateOrder()       ← hook (subclass may override)
 *   2. calculateDiscount()   ← abstract (must implement)
 *   3. chargePayment()       ← abstract (must implement)
 *   4. saveRecord()          ← abstract (must implement)
 *   5. printReceipt()        ← hook (subclass may override)
 */
public abstract class CheckoutTemplate {

    /** The invariant checkout algorithm */
    public final Payment checkout(Order order, PaymentMethod method, BigDecimal rawDiscount, User waiter) {
        validateOrder(order);                          // Step 1
        BigDecimal discount = calculateDiscount(order, rawDiscount);  // Step 2
        Payment payment = chargePayment(order, method, discount, waiter); // Step 3
        saveRecord(payment);                           // Step 4
        printReceipt(payment);                         // Step 5
        return payment;
    }

    /** Default: check order is not already paid (can be overridden) */
    protected void validateOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
    }

    /** Subclass must define how discount is computed */
    protected abstract BigDecimal calculateDiscount(Order order, BigDecimal rawDiscount);

    /** Subclass must define how payment is charged */
    protected abstract Payment chargePayment(Order order, PaymentMethod method, BigDecimal discount, User waiter);

    /** Subclass must define how record is saved */
    protected abstract void saveRecord(Payment payment);

    /** Default: just log (subclass may send to printer) */
    protected void printReceipt(Payment payment) {
        System.out.println("[TEMPLATE] Receipt for order " + payment.getOrder().getOrderNumber()
                + " — Final: " + payment.getFinalAmount());
    }
}
