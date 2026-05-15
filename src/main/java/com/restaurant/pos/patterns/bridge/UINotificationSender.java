package com.restaurant.pos.patterns.bridge;

import org.springframework.stereotype.Component;

/**
 * Concrete Implementation A: In-app UI notification (logs to console; in real app would use WebSocket)
 */
@Component("uiNotificationSender")
public class UINotificationSender implements NotificationSender {
    @Override
    public void send(String recipient, String subject, String message) {
        System.out.println("[UI NOTIFICATION → " + recipient + "] " + subject + ": " + message);
    }
}
