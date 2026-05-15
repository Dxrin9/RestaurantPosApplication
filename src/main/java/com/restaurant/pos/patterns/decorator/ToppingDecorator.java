package com.restaurant.pos.patterns.decorator;

/** Abstract Decorator base class */
public abstract class ToppingDecorator implements PricedItem {
    protected final PricedItem wrapped;

    protected ToppingDecorator(PricedItem wrapped) {
        this.wrapped = wrapped;
    }
}
