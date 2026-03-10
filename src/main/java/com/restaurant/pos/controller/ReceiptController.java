package com.restaurant.pos.controller;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.Payment;
import com.restaurant.pos.repository.OrderRepository;
import com.restaurant.pos.repository.PaymentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/receipt")
public class ReceiptController {

    private final PaymentRepository paymentRepository;
    private final OrderRepository   orderRepository;

    public ReceiptController(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository   = orderRepository;
    }

    @GetMapping("/{paymentId}")
    public String receiptPage(@PathVariable Long paymentId, Model model) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        model.addAttribute("payment", payment);
        model.addAttribute("order", payment.getOrder());
        return "receipt";
    }

    /** Find receipt by order ID - used in admin order list */
    @GetMapping("/order/{orderId}")
    public String receiptByOrder(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new RuntimeException("No payment for order " + orderId));
        return "redirect:/receipt/" + payment.getId();
    }
}
