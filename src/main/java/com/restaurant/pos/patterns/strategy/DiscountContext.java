package com.restaurant.pos.patterns.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** Context that holds the current discount strategy and applies it */
@Component
public class DiscountContext {

    private DiscountStrategy strategy = new NoDiscountStrategy();

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public BigDecimal calculateDiscount(BigDecimal total) {
        return strategy.apply(total);
    }

    public String getStrategyDescription() {
        return strategy.getDescription();
    }
}
