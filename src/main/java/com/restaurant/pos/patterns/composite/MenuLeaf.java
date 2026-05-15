package com.restaurant.pos.patterns.composite;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * LEAF: A single menu item — has no children.
 */
public class MenuLeaf implements MenuComponent {

    private final String name;
    private final BigDecimal price;

    public MenuLeaf(String name, BigDecimal price) {
        this.name  = name;
        this.price = price;
    }

    @Override public String getName()  { return name; }
    @Override public BigDecimal getPrice() { return price; }
    @Override public List<MenuComponent> getChildren() { return Collections.emptyList(); }

    @Override
    public void display(int depth) {
        System.out.println("  ".repeat(depth) + "- " + name + " (" + price + ")");
    }
}
