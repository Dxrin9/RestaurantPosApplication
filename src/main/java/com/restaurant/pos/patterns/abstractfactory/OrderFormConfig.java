package com.restaurant.pos.patterns.abstractfactory;

/**
 * Abstract product: configuration for the order form.
 */
public interface OrderFormConfig {
    boolean isDiscountAllowed();
    int getMaxItemsPerOrder();
    String getFormTitle();
}
