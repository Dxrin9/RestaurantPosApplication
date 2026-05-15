package com.restaurant.pos.patterns.state;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.enums.OrderStatus;

/** State: Order has been SENT to stations */
public class SentOrderState implements OrderState {

    @Override
    public void send(Order order) {
        System.out.println("[STATE] Order already sent.");
    }

    @Override
    public void markReady(Order order) {
        order.setStatus(OrderStatus.READY);
        System.out.println("[STATE] Order " + order.getOrderNumber() + " → READY");
    }

    @Override
    public void pay(Order order) {
        // Allow paying even if not all items ready
        order.setStatus(OrderStatus.PAID);
        System.out.println("[STATE] Order " + order.getOrderNumber() + " → PAID (from SENT)");
    }

    @Override
    public void cancel(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
        System.out.println("[STATE] Order " + order.getOrderNumber() + " → CANCELLED");
    }

    @Override
    public String getStateName() { return "SENT"; }
}
