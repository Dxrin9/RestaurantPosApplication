package com.restaurant.pos.patterns.adapter;

import java.math.BigDecimal;

/**
 * ============================================================
 * DESIGN PATTERN #4: ADAPTER
 * ============================================================
 * The PaymentProcessor is the TARGET interface that our system uses.
 * External payment gateways (Stripe, PayPal) have their own incompatible APIs.
 * Adapters convert them to our unified interface.
 *
 * This allows swapping payment providers without changing the calling code.
 */
public interface PaymentProcessor {
    boolean charge(BigDecimal amount, String reference);
    String getProviderName();
}
