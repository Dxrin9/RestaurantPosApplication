//package com.restaurant.pos.controller;
//
//import com.restaurant.pos.dto.PaymentDto;
//import com.restaurant.pos.entity.Order;
//import com.restaurant.pos.entity.Payment;
//import com.restaurant.pos.entity.User;
//import com.restaurant.pos.patterns.facade.PlaceOrderFacade;
//import com.restaurant.pos.service.OrderService;
//import com.restaurant.pos.service.UserService;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.math.BigDecimal;
//
//@Controller
//@RequestMapping("/payment")
//public class PaymentController {
//
//    private final OrderService     orderService;
//    private final PlaceOrderFacade placeOrderFacade;
//    private final UserService      userService;
//
//    public PaymentController(OrderService orderService, PlaceOrderFacade placeOrderFacade, UserService userService) {
//        this.orderService     = orderService;
//        this.placeOrderFacade = placeOrderFacade;
//        this.userService      = userService;
//    }
//
//    @GetMapping("/{orderId}")
//    public String paymentPage(@PathVariable Long orderId, Model model) {
//        Order order = orderService.getOrder(orderId);
//        model.addAttribute("order", order);
//        model.addAttribute("paymentDto", new PaymentDto());
//        return "payment";
//    }
//
//    @PostMapping("/process")
//    public String processPayment(@ModelAttribute PaymentDto dto,
//                                 Authentication auth,
//                                 RedirectAttributes ra) {
//        try {
//            User waiter = userService.findByUsername(auth.getName());
//            BigDecimal discount = dto.getDiscountAmount() != null ? dto.getDiscountAmount() : BigDecimal.ZERO;
//            Payment payment = placeOrderFacade.processPayment(dto.getOrderId(), dto.getMethod(), discount, waiter);
//            return "redirect:/receipt/" + payment.getId();
//        } catch (Exception e) {
//            ra.addFlashAttribute("error", e.getMessage());
//            return "redirect:/payment/" + dto.getOrderId();
//        }
//    }
//}
