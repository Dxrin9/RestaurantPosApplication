package com.restaurant.pos.patterns.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Applies a percentage discount (e.g., 10% off) */
public class PercentageDiscountStrategy implements DiscountStrategy {

    private final BigDecimal percentage; // e.g., 10 for 10%

    public PercentageDiscountStrategy(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public BigDecimal apply(BigDecimal total) {
        return total.multiply(percentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescription() {
        return percentage + "% Discount";
    }
}
