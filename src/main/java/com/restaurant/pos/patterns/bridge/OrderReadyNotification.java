package com.restaurant.pos.patterns.bridge;

/**
 * Refined Abstraction A: Notifies waiter that their order item is ready.
 */
public class OrderReadyNotification extends Notification {

    public OrderReadyNotification(NotificationSender sender) {
        super(sender);
    }

    @Override
    public void notify(String recipient, String context) {
        sender.send(recipient, "Order Item Ready", "Item ready for table: " + context);
    }
}
