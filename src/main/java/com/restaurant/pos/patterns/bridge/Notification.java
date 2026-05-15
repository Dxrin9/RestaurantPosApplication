package com.restaurant.pos.patterns.bridge;

/**
 * Abstraction base — holds a reference to an implementation (sender).
 */
public abstract class Notification {

    /** Bridge: abstraction holds reference to implementation */
    protected final NotificationSender sender;

    protected Notification(NotificationSender sender) {
        this.sender = sender;
    }

    public abstract void notify(String recipient, String context);
}
