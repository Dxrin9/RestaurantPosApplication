package com.restaurant.pos.patterns.observer;

import com.restaurant.pos.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SUBJECT: publishes events when a station marks an item ready.
 * Maintains a list of observers to notify.
 */
@Component
public class StationEventPublisher {

    private final List<ItemReadyObserver> observers = new ArrayList<>();

    public void register(ItemReadyObserver observer) {
        observers.add(observer);
    }

    public void unregister(ItemReadyObserver observer) {
        observers.remove(observer);
    }

    /** Called by StationService when item is marked DONE */
    public void publishItemReady(OrderItem item) {
        for (ItemReadyObserver observer : observers) {
            observer.onItemReady(item);
        }
    }
}
