package com.restaurant.pos.patterns.template;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.Payment;
import com.restaurant.pos.entity.User;
import com.restaurant.pos.enums.PaymentMethod;
import com.restaurant.pos.patterns.adapter.PaymentAdapterFactory;
import com.restaurant.pos.patterns.strategy.DiscountContext;
import com.restaurant.pos.patterns.strategy.FixedDiscountStrategy;
import com.restaurant.pos.patterns.strategy.NoDiscountStrategy;
import com.restaurant.pos.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Concrete implementation of CheckoutTemplate for standard orders.
 */
public class StandardCheckout extends CheckoutTemplate {

    private final PaymentAdapterFactory adapterFactory;
    private final PaymentRepository     paymentRepository;

    public StandardCheckout(PaymentAdapterFactory adapterFactory, PaymentRepository paymentRepository) {
        this.adapterFactory    = adapterFactory;
        this.paymentRepository = paymentRepository;
    }

    @Override
    protected BigDecimal calculateDiscount(Order order, BigDecimal rawDiscount) {
        DiscountContext ctx = new DiscountContext();
        if (rawDiscount != null && rawDiscount.compareTo(BigDecimal.ZERO) > 0) {
            ctx.setStrategy(new FixedDiscountStrategy(rawDiscount));
        } else {
            ctx.setStrategy(new NoDiscountStrategy());
        }
        return ctx.calculateDiscount(order.getTotal());
    }

    @Override
    protected Payment chargePayment(Order order, PaymentMethod method, BigDecimal discount, User waiter) {
        BigDecimal total  = order.getTotal();
        BigDecimal finalAmount = total.subtract(discount).max(BigDecimal.ZERO);

        adapterFactory.getAdapter(method).charge(finalAmount, order.getOrderNumber());

        return Payment.builder()
                .order(order)
                .method(method)
                .totalAmount(total)
                .discountAmount(discount)
                .finalAmount(finalAmount)
                .waiter(waiter)
                .paidAt(LocalDateTime.now())
                .build();
    }

    @Override
    protected void saveRecord(Payment payment) {
        paymentRepository.save(payment);
    }
}
