package com.restaurant.pos.patterns.chain;

import com.restaurant.pos.entity.Order;
import org.springframework.stereotype.Component;

/**
 * Assembles and runs the full order validation chain.
 * Call validate(order) to get null (valid) or an error message.
 */
@Component
public class OrderValidationChain {

    public String validate(Order order) {
        // Build the chain: Table → Items → Status
        OrderValidator tableValidator  = new TableValidator();
        OrderValidator itemsValidator  = new ItemsValidator();
        OrderValidator statusValidator = new StatusValidator();

        tableValidator.setNext(itemsValidator).setNext(statusValidator);

        return tableValidator.validate(order);
    }
}
