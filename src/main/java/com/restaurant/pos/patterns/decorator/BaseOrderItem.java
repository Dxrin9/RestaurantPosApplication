package com.restaurant.pos.patterns.decorator;

import java.math.BigDecimal;

/** Base component: a plain menu item with name and price */
public class BaseOrderItem implements PricedItem {
    private final String name;
    private final BigDecimal price;

    public BaseOrderItem(String name, BigDecimal price) {
        this.name  = name;
        this.price = price;
    }

    @Override public String getDescription() { return name; }
    @Override public BigDecimal getPrice()   { return price; }
}
