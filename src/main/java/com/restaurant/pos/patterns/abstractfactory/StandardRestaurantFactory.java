package com.restaurant.pos.patterns.abstractfactory;

import org.springframework.stereotype.Component;

/** Standard restaurant mode: normal per-table à la carte service */
@Component("standardFactory")
public class StandardRestaurantFactory implements RestaurantModeFactory {

    @Override
    public OrderFormConfig createOrderFormConfig() {
        return new OrderFormConfig() {
            public boolean isDiscountAllowed() { return true; }
            public int getMaxItemsPerOrder()   { return 50; }
            public String getFormTitle()       { return "Standard Order"; }
        };
    }

    @Override
    public ReceiptConfig createReceiptConfig() {
        return new ReceiptConfig() {
            public boolean showLogo()          { return true; }
            public String getFooterMessage()   { return "Thank you for dining with us!"; }
            public boolean showItemizedTax()   { return false; }
        };
    }
}
