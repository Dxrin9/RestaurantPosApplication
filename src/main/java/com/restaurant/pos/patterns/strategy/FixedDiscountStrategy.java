package com.restaurant.pos.patterns.strategy;

import java.math.BigDecimal;

/** Applies a fixed amount discount */
public class FixedDiscountStrategy implements DiscountStrategy {

    private final BigDecimal amount;

    public FixedDiscountStrategy(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public BigDecimal apply(BigDecimal total) {
        // Discount cannot exceed total
        return amount.compareTo(total) > 0 ? total : amount;
    }

    @Override
    public String getDescription() {
        return "Fixed Discount: -" + amount;
    }
}
