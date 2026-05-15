package com.restaurant.pos.patterns.state;

import com.restaurant.pos.enums.OrderStatus;
import org.springframework.stereotype.Component;

/** Creates the correct OrderState object for a given status */
@Component
public class OrderStateFactory {

    public OrderState getState(OrderStatus status) {
        return switch (status) {
            case NEW       -> new NewOrderState();
            case SENT      -> new SentOrderState();
            case READY     -> new SentOrderState(); // READY allows payment
            case PAID      -> new PaidOrderState();
            case CANCELLED -> new CancelledOrderState();
        };
    }
}
