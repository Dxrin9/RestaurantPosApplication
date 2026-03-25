package com.restaurant.pos.patterns.adapter;

import java.math.BigDecimal;

/**
 * ADAPTEE: Mock Stripe SDK (incompatible API, uses cents)
 */
class MockStripeSDK {
    public boolean processPaymentInCents(long amountCents, String orderId) {
        System.out.println("[STRIPE MOCK] Charging " + amountCents + " cents for order " + orderId);
        return true; // Always succeeds in mock
    }
}

/**
 * ADAPTER: Converts MockStripeSDK to our PaymentProcessor interface
 */
public class StripePaymentAdapter implements PaymentProcessor {

    private final MockStripeSDK stripe = new MockStripeSDK();

    @Override
    public boolean charge(BigDecimal amount, String reference) {
        // Stripe uses cents (integer), our system uses BigDecimal (euros/dollars)
        long cents = amount.multiply(BigDecimal.valueOf(100)).longValue();
        return stripe.processPaymentInCents(cents, reference);
    }

    @Override
    public String getProviderName() {
        return "Stripe";
    }
}
