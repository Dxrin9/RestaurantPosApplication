package com.restaurant.pos.patterns.facade;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.Payment;
import com.restaurant.pos.entity.RestaurantTable;
import com.restaurant.pos.entity.User;
import com.restaurant.pos.enums.PaymentMethod;
import com.restaurant.pos.patterns.adapter.PaymentAdapterFactory;
import com.restaurant.pos.patterns.adapter.PaymentProcessor;
import com.restaurant.pos.patterns.chain.OrderValidationChain;
import com.restaurant.pos.patterns.factory.TicketPrinterFactory;
import com.restaurant.pos.patterns.mediator.PosMediator;
import com.restaurant.pos.patterns.observer.StationEventPublisher;
import com.restaurant.pos.patterns.state.OrderStateFactory;
import com.restaurant.pos.repository.OrderItemRepository;
import com.restaurant.pos.repository.OrderRepository;
import com.restaurant.pos.repository.PaymentRepository;
import com.restaurant.pos.repository.TableRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ============================================================
 * DESIGN PATTERN #18: FACADE
 * ============================================================
 * PlaceOrderFacade provides a SIMPLE interface to a complex system.
 * Instead of the controller talking to 8+ services/patterns directly,
 * it calls one facade method.
 *
 * sendOrder(orderId)  → validate → change state → print tickets → notify station
 * processPayment(...) → charge → update order → free table → save payment
 */
@Component
public class PlaceOrderFacade {

    private final OrderRepository        orderRepository;
    private final OrderItemRepository    orderItemRepository;
    private final PaymentRepository      paymentRepository;
    private final TableRepository        tableRepository;
    private final OrderValidationChain   validationChain;
    private final OrderStateFactory      stateFactory;
    private final TicketPrinterFactory   ticketFactory;
    private final PaymentAdapterFactory  paymentAdapterFactory;
    private final PosMediator            posMediator;
    private final StationEventPublisher  stationPublisher;

    public PlaceOrderFacade(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            PaymentRepository paymentRepository,
            TableRepository tableRepository,
            OrderValidationChain validationChain,
            OrderStateFactory stateFactory,
            TicketPrinterFactory ticketFactory,
            PaymentAdapterFactory paymentAdapterFactory,
            PosMediator posMediator,
            StationEventPublisher stationPublisher) {
        this.orderRepository       = orderRepository;
        this.orderItemRepository   = orderItemRepository;
        this.paymentRepository     = paymentRepository;
        this.tableRepository       = tableRepository;
        this.validationChain       = validationChain;
        this.stateFactory          = stateFactory;
        this.ticketFactory         = ticketFactory;
        this.paymentAdapterFactory = paymentAdapterFactory;
        this.posMediator           = posMediator;
        this.stationPublisher      = stationPublisher;
    }

    /**
     * Send order to kitchen/bar/pizza stations.
     * Validates → transitions state → prints tickets.
     * @return null on success, error message on failure
     */
    public String sendOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        // Chain of Responsibility: validate
        String validationError = validationChain.validate(order);
        if (validationError != null) return validationError;

        // State pattern: transition to SENT
        stateFactory.getState(order.getStatus()).send(order);

        // Print tickets per station (Factory pattern)
        order.getItems().stream()
                .filter(i -> !i.isVoided())
                .forEach(item -> {
                    String ticket = ticketFactory.create(item.getStationType()).printTicket(item);
                    System.out.println(ticket);
                });

        orderRepository.save(order);
        return null; // success
    }

    /**
     * Process payment for an order.
     * @param discountAmount fixed discount to apply (0 for none)
     */
    public Payment processPayment(Long orderId, PaymentMethod method, BigDecimal discountAmount, User waiter) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        BigDecimal total = order.getTotal();
        BigDecimal discount = discountAmount != null ? discountAmount : BigDecimal.ZERO;
        BigDecimal finalAmount = total.subtract(discount).max(BigDecimal.ZERO);

        // Adapter: charge via correct payment gateway
        PaymentProcessor processor = paymentAdapterFactory.getAdapter(method);
        processor.charge(finalAmount, order.getOrderNumber());

        // State: transition to PAID
        stateFactory.getState(order.getStatus()).pay(order);
        orderRepository.save(order);

        // Free the table
        RestaurantTable table = order.getTable();
        table.setOccupied(false);
        tableRepository.save(table);

        // Save payment record
        Payment payment = Payment.builder()
                .order(order)
                .method(method)
                .totalAmount(total)
                .discountAmount(discount)
                .finalAmount(finalAmount)
                .waiter(waiter)
                .paidAt(LocalDateTime.now())
                .build();
        Payment saved = paymentRepository.save(payment);

        // Mediator: broadcast event
        posMediator.onOrderPaid(order);

        return saved;
    }
}
