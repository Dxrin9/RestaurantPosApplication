package com.restaurant.pos.patterns.adapter;

import com.restaurant.pos.enums.PaymentMethod;
import org.springframework.stereotype.Component;

/** Factory that selects the correct payment adapter at runtime */
@Component
public class PaymentAdapterFactory {

    public PaymentProcessor getAdapter(PaymentMethod method) {
        return switch (method) {
            case CASH -> new CashPaymentAdapter();
            case CARD -> new StripePaymentAdapter();
        };
    }
}
