package com.restaurant.pos.patterns.bridge;

import org.springframework.stereotype.Component;

/**
 * Concrete Implementation B: Email notification (mocked — no real SMTP in this demo)
 */
@Component("emailNotificationSender")
public class EmailNotificationSender implements NotificationSender {
    @Override
    public void send(String recipient, String subject, String message) {
        System.out.println("[EMAIL → " + recipient + "] Subject: " + subject + " | Body: " + message);
    }
}
