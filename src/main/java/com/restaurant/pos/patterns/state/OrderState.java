package com.restaurant.pos.patterns.state;

import com.restaurant.pos.entity.Order;

/**
 * ============================================================
 * DESIGN PATTERN #16: STATE
 * ============================================================
 * Each order state defines what transitions are allowed.
 * Instead of a giant if/else in the service, each state handles its own logic.
 *
 * States: NewOrderState → SentOrderState → ReadyOrderState → PaidOrderState
 *                                                           → CancelledOrderState
 */
public interface OrderState {
    void send(Order order);
    void markReady(Order order);
    void pay(Order order);
    void cancel(Order order);
    String getStateName();
}
