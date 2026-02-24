package com.restaurant.pos.patterns.factory;

import com.restaurant.pos.enums.StationType;
import org.springframework.stereotype.Component;

/**
 * Factory that creates the correct TicketPrinter based on station type.
 * Callers don't need to know which concrete class they're getting.
 */
@Component
public class TicketPrinterFactory {

    public TicketPrinter create(StationType stationType) {
        return switch (stationType) {
            case KITCHEN -> new KitchenTicketPrinter();
            case BAR     -> new BarTicketPrinter();
            case PIZZA   -> new PizzaTicketPrinter();
        };
    }
}
