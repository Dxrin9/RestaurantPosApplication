package com.restaurant.pos.patterns.abstractfactory;

import org.springframework.stereotype.Component;

/** Banquet mode: fixed menus for events, no per-item discounts */
@Component("banquetFactory")
public class BanquetRestaurantFactory implements RestaurantModeFactory {

    @Override
    public OrderFormConfig createOrderFormConfig() {
        return new OrderFormConfig() {
            public boolean isDiscountAllowed() { return false; }
            public int getMaxItemsPerOrder()   { return 200; }
            public String getFormTitle()       { return "Banquet Order"; }
        };
    }

    @Override
    public ReceiptConfig createReceiptConfig() {
        return new ReceiptConfig() {
            public boolean showLogo()          { return true; }
            public String getFooterMessage()   { return "Thank you for choosing us for your event!"; }
            public boolean showItemizedTax()   { return true; }
        };
    }
}
