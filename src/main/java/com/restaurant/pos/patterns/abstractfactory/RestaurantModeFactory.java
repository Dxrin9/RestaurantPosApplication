package com.restaurant.pos.patterns.abstractfactory;

/**
 * ============================================================
 * DESIGN PATTERN #3: ABSTRACT FACTORY
 * ============================================================
 * Creates families of related objects without specifying concrete classes.
 * Here: the restaurant can operate in "Standard" mode or "Banquet" mode.
 * Each mode produces differently configured UI components and behaviors.
 *
 * StandardRestaurantFactory → normal per-table service
 * BanquetRestaurantFactory  → large-group fixed-menu service
 */
public interface RestaurantModeFactory {
    OrderFormConfig createOrderFormConfig();
    ReceiptConfig createReceiptConfig();
}
