package com.restaurant.pos.patterns.prototype;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.OrderItem;
import com.restaurant.pos.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * DESIGN PATTERN #11: PROTOTYPE
 * ============================================================
 * Creates a copy of a previous order to use as a starting point.
 * Useful for regulars who always order the same thing.
 *
 * Usage: OrderPrototype.clone(previousOrder) → new Order with same items
 * The clone gets NEW status, new order number slot (assigned by service).
 */
@Component
public class OrderPrototype {

    /**
     * Deep-clones an order (copies items, resets status/timestamps).
     * The cloned order is detached from JPA — save it separately.
     */
    public Order clone(Order source) {
        Order copy = new Order();
        copy.setTable(source.getTable());
        copy.setWaiter(source.getWaiter());
        copy.setNotes(source.getNotes());
        copy.setStatus(OrderStatus.NEW);
        copy.setCreatedAt(LocalDateTime.now());
        copy.setUpdatedAt(LocalDateTime.now());
        // orderNumber is assigned later by service layer

        // Deep copy items
        List<OrderItem> copiedItems = new ArrayList<>();
        for (OrderItem sourceItem : source.getItems()) {
            if (!sourceItem.isVoided()) { // Don't copy voided items
                OrderItem itemCopy = new OrderItem();
                itemCopy.setOrder(copy);
                itemCopy.setMenuItem(sourceItem.getMenuItem());
                itemCopy.setQuantity(sourceItem.getQuantity());
                itemCopy.setUnitPrice(sourceItem.getUnitPrice());
                itemCopy.setNotes(sourceItem.getNotes());
                itemCopy.setExtras(sourceItem.getExtras());
                itemCopy.setStationType(sourceItem.getStationType());
                itemCopy.setVoided(false);
                itemCopy.setReady(false);
                copiedItems.add(itemCopy);
            }
        }
        copy.setItems(copiedItems);
        return copy;
    }
}
