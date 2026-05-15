package com.restaurant.pos.patterns.composite;

import java.math.BigDecimal;
import java.util.List;

/**
 * ============================================================
 * DESIGN PATTERN #12: COMPOSITE
 * ============================================================
 * Allows treating individual menu items and menu categories uniformly.
 * A MenuComposite (category) can contain MenuLeaf (items) or other MenuComposites.
 *
 * This models the real menu structure:
 *   Drinks (category)
 *     ├── Hot Drinks (sub-category)
 *     │     ├── Espresso (item)
 *     │     └── Cappuccino (item)
 *     └── Cold Drinks (sub-category)
 *           └── Orange Juice (item)
 */
public interface MenuComponent {
    String getName();
    BigDecimal getPrice();   // Categories return sum of children, leaves return item price
    void display(int depth); // Print tree structure
    List<MenuComponent> getChildren(); // Empty for leaves
}
