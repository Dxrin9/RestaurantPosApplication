package com.restaurant.pos.patterns.decorator;

import java.math.BigDecimal;

/** Concrete Decorator: adds extra cheese */
public class ExtraCheese extends ToppingDecorator {

    private static final BigDecimal EXTRA_CHEESE_PRICE = new BigDecimal("1.00");

    public ExtraCheese(PricedItem wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Extra Cheese";
    }

    @Override
    public BigDecimal getPrice() {
        return wrapped.getPrice().add(EXTRA_CHEESE_PRICE);
    }
}
