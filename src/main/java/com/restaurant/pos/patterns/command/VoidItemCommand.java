package com.restaurant.pos.patterns.command;

import com.restaurant.pos.entity.OrderItem;

/** Command: Void (cancel) a specific order item */
public class VoidItemCommand implements PosCommand {
    private final OrderItem item;
    private boolean previousVoidedState;

    public VoidItemCommand(OrderItem item) {
        this.item = item;
    }

    @Override
    public void execute() {
        previousVoidedState = item.isVoided();
        item.setVoided(true);
    }

    @Override
    public void undo() {
        item.setVoided(previousVoidedState);
    }

    @Override
    public String getDescription() {
        return "Void item: " + item.getMenuItem().getName();
    }
}
