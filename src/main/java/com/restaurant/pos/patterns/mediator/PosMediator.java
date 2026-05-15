package com.restaurant.pos.patterns.mediator;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.OrderItem;
import com.restaurant.pos.patterns.bridge.NotificationSender;
import com.restaurant.pos.patterns.bridge.OrderReadyNotification;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * ============================================================
 * DESIGN PATTERN #9: MEDIATOR
 * ============================================================
 * The PosMediator coordinates communication between all POS components:
 * OrderService, StationService, PaymentService, NotificationService.
 *
 * Instead of these services calling each other directly (tight coupling),
 * they all communicate through the mediator. This reduces dependencies
 * and makes the system easier to maintain.
 *
 * Events handled:
 *   - onItemReady(item)  → notifies waiter via NotificationSender
 *   - onOrderPaid(order) → marks table free, closes shift tracking
 *   - onOrderCancelled(order) → releases table
 */
@Component
public class PosMediator {

    private final NotificationSender notificationSender;

    public PosMediator(@Qualifier("uiNotificationSender") NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    /**
     * Called by StationController when kitchen/bar/pizza marks item DONE.
     * Mediator notifies the waiter (using Bridge + Observer).
     */
    public void onItemReady(OrderItem item) {
        String waiterName = item.getOrder().getWaiter() != null
                ? item.getOrder().getWaiter().getUsername()
                : "waiter";
        String tableInfo = "Table #" + item.getOrder().getTable().getTableNumber()
                + " — " + item.getMenuItem().getName();

        new OrderReadyNotification(notificationSender).notify(waiterName, tableInfo);
    }

    /**
     * Called when an order is fully paid.
     */
    public void onOrderPaid(Order order) {
        System.out.println("[MEDIATOR] Order " + order.getOrderNumber() + " paid. Table #"
                + order.getTable().getTableNumber() + " is now free.");
    }

    /**
     * Called when an order is cancelled by admin.
     */
    public void onOrderCancelled(Order order) {
        System.out.println("[MEDIATOR] Order " + order.getOrderNumber() + " cancelled.");
    }
}
