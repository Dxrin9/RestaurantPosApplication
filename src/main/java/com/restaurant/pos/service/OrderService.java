package com.restaurant.pos.service;

import com.restaurant.pos.dto.AddItemDto;
import com.restaurant.pos.entity.*;
import com.restaurant.pos.enums.OrderStatus;
import com.restaurant.pos.patterns.builder.OrderBuilder;
import com.restaurant.pos.patterns.singleton.OrderNumberGenerator;
import com.restaurant.pos.repository.MenuItemRepository;
import com.restaurant.pos.repository.OrderItemRepository;
import com.restaurant.pos.repository.OrderRepository;
import com.restaurant.pos.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Low-level order management service.
 * Handles CRUD operations for Orders and OrderItems.
 * Business orchestration (validation, ticket printing, payment) lives in PlaceOrderFacade.
 */
@Service
@Transactional
public class OrderService {

    private final OrderRepository      orderRepository;
    private final OrderItemRepository  orderItemRepository;
    private final MenuItemRepository   menuItemRepository;
    private final TableRepository      tableRepository;
    private final OrderBuilder         orderBuilder;
    private final OrderNumberGenerator orderNumberGenerator;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        MenuItemRepository menuItemRepository,
                        TableRepository tableRepository,
                        OrderBuilder orderBuilder,
                        OrderNumberGenerator orderNumberGenerator) {
        this.orderRepository      = orderRepository;
        this.orderItemRepository  = orderItemRepository;
        this.menuItemRepository   = menuItemRepository;
        this.tableRepository      = tableRepository;
        this.orderBuilder         = orderBuilder;
        this.orderNumberGenerator = orderNumberGenerator;
    }

    /**
     * Returns the active (NEW or SENT) order for a table, or creates one if none exists.
     * Uses the Builder pattern to construct new orders.
     */
    public Order getOrCreateOrder(Long tableId, User waiter) {
        RestaurantTable table = tableRepository.findById(tableId).orElseThrow();

        return orderRepository
                .findByTableAndStatusIn(table, List.of(OrderStatus.NEW, OrderStatus.SENT))
                .orElseGet(() -> {
                    Order newOrder = orderBuilder
                            .forTable(table)
                            .byWaiter(waiter)
                            .build(orderNumberGenerator);
                    return orderRepository.save(newOrder);
                });
    }

    /** Fetch an order by ID — throws if not found */
    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    /** All orders descending by creation date — for admin dashboard */
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Add a menu item to an existing order.
     * Snapshots the current price into the OrderItem.
     */
    public Order addItem(Long orderId, AddItemDto dto) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        if (order.getStatus() != OrderStatus.NEW) {
            throw new IllegalStateException("Cannot add items to an order that is not NEW.");
        }

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId()).orElseThrow();

        OrderItem item = OrderItem.builder()
                .order(order)
                .menuItem(menuItem)
                .quantity(dto.getQuantity())
                .unitPrice(menuItem.getPrice())
                .notes(dto.getNotes())
                .extras(dto.getExtras())
                .stationType(menuItem.getStationType())
                .build();

        orderItemRepository.save(item);
        order.getItems().add(item);
        return orderRepository.save(order);
    }

    /** Void (soft-delete) a single order item */
    public void removeItem(Long itemId) {
        OrderItem item = orderItemRepository.findById(itemId).orElseThrow();
        item.setVoided(true);
        orderItemRepository.save(item);
    }

    /** Transition the order to the given status */
    public Order updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
