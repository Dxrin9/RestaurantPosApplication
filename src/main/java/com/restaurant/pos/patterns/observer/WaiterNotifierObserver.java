package com.restaurant.pos.patterns.observer;

import com.restaurant.pos.entity.OrderItem;
import com.restaurant.pos.patterns.mediator.PosMediator;
import org.springframework.stereotype.Component;

/**
 * CONCRETE OBSERVER: Notifies the waiter via the PosMediator.
 */
@Component
public class WaiterNotifierObserver implements ItemReadyObserver {

    private final PosMediator posMediator;

    public WaiterNotifierObserver(PosMediator posMediator) {
        this.posMediator = posMediator;
    }

    @Override
    public void onItemReady(OrderItem item) {
        posMediator.onItemReady(item);
    }
}
