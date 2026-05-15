package com.restaurant.pos.patterns.decorator;

import java.math.BigDecimal;

/** Generic topping decorator — accepts any topping name and price */
public class ExtraTopping extends ToppingDecorator {

    private final String toppingName;
    private final BigDecimal toppingPrice;

    public ExtraTopping(PricedItem wrapped, String toppingName, BigDecimal toppingPrice) {
        super(wrapped);
        this.toppingName  = toppingName;
        this.toppingPrice = toppingPrice;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + " + toppingName;
    }

    @Override
    public BigDecimal getPrice() {
        return wrapped.getPrice().add(toppingPrice);
    }
}
