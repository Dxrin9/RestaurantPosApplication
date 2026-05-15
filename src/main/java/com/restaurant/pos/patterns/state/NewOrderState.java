package com.restaurant.pos.patterns.state;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.enums.OrderStatus;

/**
 * State: Order is NEW (just created, not yet sent to kitchen/bar/pizza)
 */
public class NewOrderState implements OrderState {

    @Override
    public void send(Order order) {
        order.setStatus(OrderStatus.SENT);
        System.out.println("[STATE] Order " + order.getOrderNumber() + " → SENT");
    }

    @Override
    public void markReady(Order order) {
        throw new IllegalStateException("Cannot mark ready: order not yet sent.");
    }

    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Cannot pay: order not yet sent.");
    }

    @Override
    public void cancel(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
        System.out.println("[STATE] Order " + order.getOrderNumber() + " → CANCELLED");
    }

    @Override
    public String getStateName() { return "NEW"; }
}
