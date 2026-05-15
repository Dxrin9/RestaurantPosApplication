package com.restaurant.pos.patterns.strategy;

import java.math.BigDecimal;

/** No discount applied */
public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public BigDecimal apply(BigDecimal total) { return BigDecimal.ZERO; }
    @Override
    public String getDescription() { return "No Discount"; }
}
