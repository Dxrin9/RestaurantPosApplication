package com.restaurant.pos.patterns.state;

import com.restaurant.pos.entity.Order;

/** Terminal state: Order is CANCELLED */
public class CancelledOrderState implements OrderState {
    @Override public void send(Order order)      { throw new IllegalStateException("Order is cancelled."); }
    @Override public void markReady(Order order) { throw new IllegalStateException("Order is cancelled."); }
    @Override public void pay(Order order)       { throw new IllegalStateException("Order is cancelled."); }
    @Override public void cancel(Order order)    { System.out.println("[STATE] Already cancelled."); }
    @Override public String getStateName()       { return "CANCELLED"; }
}
