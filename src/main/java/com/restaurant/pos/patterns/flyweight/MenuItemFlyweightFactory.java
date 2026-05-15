package com.restaurant.pos.patterns.flyweight;

import com.restaurant.pos.entity.MenuItem;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * DESIGN PATTERN #19: FLYWEIGHT
 * ============================================================
 * Shares MenuItem metadata objects to reduce memory usage.
 * In a large restaurant with thousands of orders, each order references
 * the same MenuItem (intrinsic state) rather than copying its data.
 *
 * The FlyweightFactory caches MenuItem instances by ID.
 * OrderItems store only extrinsic state (quantity, notes, etc.) separately.
 *
 * Note: Spring's JPA entity cache already embodies this concept,
 * but this class makes it explicit for demonstration purposes.
 */
@Component
public class MenuItemFlyweightFactory {

    /** Cache: menuItemId → MenuItem (shared/flyweight) */
    private final Map<Long, MenuItem> cache = new HashMap<>();

    /**
     * Returns the shared (flyweight) MenuItem instance.
     * If not cached, stores it for future reuse.
     */
    public MenuItem getFlyweight(MenuItem menuItem) {
        return cache.computeIfAbsent(menuItem.getId(), id -> {
            System.out.println("[FLYWEIGHT] Caching MenuItem: " + menuItem.getName());
            return menuItem;
        });
    }

    public int getCacheSize() {
        return cache.size();
    }

    public void clearCache() {
        cache.clear();
    }
}
