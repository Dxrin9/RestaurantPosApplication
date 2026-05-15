package com.restaurant.pos.patterns.strategy;

import java.math.BigDecimal;

/**
 * ============================================================
 * DESIGN PATTERN #17: STRATEGY
 * ============================================================
 * Discount calculation is a family of interchangeable algorithms.
 * Strategy allows switching discount type at runtime:
 *   - NoDiscountStrategy (0%)
 *   - PercentageDiscountStrategy (e.g., 10%)
 *   - FixedDiscountStrategy (e.g., -5.00)
 *   - HappyHourStrategy (20% off drinks)
 */
public interface DiscountStrategy {
    BigDecimal apply(BigDecimal totalAmount);
    String getDescription();
}
