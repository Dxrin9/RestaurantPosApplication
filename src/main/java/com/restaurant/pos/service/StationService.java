package com.restaurant.pos.service;

import com.restaurant.pos.entity.OrderItem;
import com.restaurant.pos.enums.StationType;
//import com.restaurant.pos.patterns.observer.StationEventPublisher;
import com.restaurant.pos.repository.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StationService {

    private final OrderItemRepository  orderItemRepository;
    //private final StationEventPublisher eventPublisher;

    public StationService(OrderItemRepository orderItemRepository /*StationEventPublisher eventPublisher*/) {
        this.orderItemRepository = orderItemRepository;
        //this.eventPublisher      = eventPublisher;
    }

    /** Get all pending (sent, not yet ready) items for a station */
    public List<OrderItem> getPendingItems(StationType stationType) {
        return orderItemRepository.findPendingItemsByStation(stationType);
    }

    /**
     * Station marks item as ready → Observer notified → Waiter alerted.
     */
    public void markItemReady(Long itemId) {
        OrderItem item = orderItemRepository.findById(itemId).orElseThrow();
        item.setReady(true);
        orderItemRepository.save(item);

        // Observer pattern: notify all observers (waiter terminal)
        //eventPublisher.publishItemReady(item);
    }
}
