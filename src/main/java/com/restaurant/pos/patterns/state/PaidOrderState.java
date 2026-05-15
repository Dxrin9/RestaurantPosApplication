package com.restaurant.pos.patterns.state;

import com.restaurant.pos.entity.Order;

/** Terminal state: Order is PAID — no further transitions allowed */
public class PaidOrderState implements OrderState {
    @Override public void send(Order order)      { throw new IllegalStateException("Order already paid."); }
    @Override public void markReady(Order order) { throw new IllegalStateException("Order already paid."); }
    @Override public void pay(Order order)       { throw new IllegalStateException("Order already paid."); }
    @Override public void cancel(Order order)    { throw new IllegalStateException("Cannot cancel a paid order."); }
    @Override public String getStateName()       { return "PAID"; }
}
