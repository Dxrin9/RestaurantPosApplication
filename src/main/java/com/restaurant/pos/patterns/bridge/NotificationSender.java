package com.restaurant.pos.patterns.bridge;

/**
 * ============================================================
 * DESIGN PATTERN #5: BRIDGE
 * ============================================================
 * Separates the ABSTRACTION (what to notify) from the IMPLEMENTATION (how to send).
 *
 * Abstraction hierarchy: Notification → OrderReadyNotification, ShiftNotification
 * Implementation hierarchy: NotificationSender → UINotificationSender, EmailNotificationSender
 *
 * This allows mixing and matching: any notification type can use any sender.
 */
public interface NotificationSender {
    void send(String recipient, String subject, String message);
}
