package com.restaurant.pos.patterns.decorator;

import java.math.BigDecimal;

/**
 * ============================================================
 * DESIGN PATTERN #13: DECORATOR
 * ============================================================
 * Adds extra ingredients/toppings to menu items dynamically at runtime.
 * Each decorator wraps a base item and adds price + description.
 *
 * Example:
 *   PricedItem pizza = new BaseOrderItem("Margherita", 9.50);
 *   pizza = new ExtraCheese(pizza);       // +1.00
 *   pizza = new ExtraTopping(pizza, "Mushrooms", 0.75);  // +0.75
 *   pizza.getDescription() → "Margherita + Extra Cheese + Mushrooms"
 *   pizza.getPrice()       → 11.25
 */
public interface PricedItem {
    String getDescription();
    BigDecimal getPrice();
}
