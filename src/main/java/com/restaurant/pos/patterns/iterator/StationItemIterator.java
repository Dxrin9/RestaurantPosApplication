package com.restaurant.pos.patterns.iterator;

import com.restaurant.pos.entity.OrderItem;
import com.restaurant.pos.enums.StationType;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * ============================================================
 * DESIGN PATTERN #8: ITERATOR
 * ============================================================
 * Provides a way to iterate over order items filtered by station type,
 * WITHOUT exposing the internal list structure.
 *
 * Usage: new StationItemIterator(order.getItems(), StationType.KITCHEN)
 * Then iterate: while(it.hasNext()) { it.next() ... }
 */
public class StationItemIterator implements Iterator<OrderItem> {

    private final List<OrderItem> items;
    private final StationType stationType;
    private int index = 0;
    private OrderItem nextItem = null;

    public StationItemIterator(List<OrderItem> items, StationType stationType) {
        this.items = items;
        this.stationType = stationType;
        advance();
    }

    /** Move index forward to the next matching item */
    private void advance() {
        nextItem = null;
        while (index < items.size()) {
            OrderItem candidate = items.get(index++);
            if (!candidate.isVoided() && candidate.getStationType() == stationType) {
                nextItem = candidate;
                break;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextItem != null;
    }

    @Override
    public OrderItem next() {
        if (nextItem == null) throw new NoSuchElementException();
        OrderItem result = nextItem;
        advance();
        return result;
    }
}
