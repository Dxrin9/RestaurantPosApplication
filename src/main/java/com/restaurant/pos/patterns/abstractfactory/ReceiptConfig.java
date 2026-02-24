package com.restaurant.pos.patterns.abstractfactory;

/** Abstract product: receipt print configuration. */
public interface ReceiptConfig {
    boolean showLogo();
    String getFooterMessage();
    boolean showItemizedTax();
}
