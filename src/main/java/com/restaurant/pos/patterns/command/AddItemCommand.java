package com.restaurant.pos.patterns.command;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.OrderItem;

/**
 * Command: Add an item to an order
 */
public class AddItemCommand implements PosCommand {
    private final Order order;
    private final OrderItem item;

    public AddItemCommand(Order order, OrderItem item) {
        this.order = order;
        this.item  = item;
    }

    @Override
    public void execute() {
        order.getItems().add(item);
        item.setOrder(order);
    }

    @Override
    public void undo() {
        order.getItems().remove(item);
    }

    @Override
    public String getDescription() {
        return "Add " + item.getMenuItem().getName() + " x" + item.getQuantity();
    }
}
