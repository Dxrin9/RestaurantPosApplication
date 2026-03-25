package com.restaurant.pos.patterns.adapter;

import java.math.BigDecimal;

/**
 * Cash payment adapter — no external gateway needed.
 * Adapts a simple "accept cash" operation to PaymentProcessor interface.
 */
public class CashPaymentAdapter implements PaymentProcessor {

    @Override
    public boolean charge(BigDecimal amount, String reference) {
        System.out.println("[CASH] Accepted " + amount + " for order " + reference);
        return true;
    }

    @Override
    public String getProviderName() {
        return "Cash";
    }
}
