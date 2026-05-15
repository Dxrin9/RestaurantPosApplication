package com.restaurant.pos.patterns.composite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * COMPOSITE: A menu category — can contain leaves and other categories.
 */
public class MenuComposite implements MenuComponent {

    private final String name;
    private final List<MenuComponent> children = new ArrayList<>();

    public MenuComposite(String name) {
        this.name = name;
    }

    public void add(MenuComponent component) {
        children.add(component);
    }

    public void remove(MenuComponent component) {
        children.remove(component);
    }

    @Override
    public String getName() { return name; }

    /** Category price = sum of all children prices */
    @Override
    public BigDecimal getPrice() {
        return children.stream()
                .map(MenuComponent::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<MenuComponent> getChildren() { return children; }

    @Override
    public void display(int depth) {
        System.out.println("  ".repeat(depth) + "[" + name + "]");
        for (MenuComponent c : children) {
            c.display(depth + 1);
        }
    }
}
