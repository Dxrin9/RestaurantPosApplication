package com.restaurant.pos.patterns.observer;

import com.restaurant.pos.entity.OrderItem;

/**
 * ============================================================
 * DESIGN PATTERN #15: OBSERVER
 * ============================================================
 * When a station (kitchen/bar/pizza) marks an item as DONE,
 * all registered observers (e.g., waiter terminal) are notified.
 *
 * Subject:  StationEventPublisher
 * Observer: ItemReadyObserver (interface) → WaiterNotifierObserver (concrete)
 */
public interface ItemReadyObserver {
    void onItemReady(OrderItem item);
}
