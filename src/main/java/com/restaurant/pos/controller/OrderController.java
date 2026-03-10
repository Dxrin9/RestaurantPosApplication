//package com.restaurant.pos.controller;
//
//import com.restaurant.pos.dto.AddItemDto;
//import com.restaurant.pos.entity.Order;
//import com.restaurant.pos.entity.User;
//import com.restaurant.pos.patterns.facade.PlaceOrderFacade;
//import com.restaurant.pos.service.MenuService;
//import com.restaurant.pos.service.OrderService;
//import com.restaurant.pos.service.UserService;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequestMapping("/order")
//public class OrderController {
//
//    private final OrderService     orderService;
//    private final MenuService      menuService;
//    private final UserService      userService;
//    private final PlaceOrderFacade placeOrderFacade;
//
//    public OrderController(OrderService orderService, MenuService menuService,
//                           UserService userService, PlaceOrderFacade placeOrderFacade) {
//        this.orderService     = orderService;
//        this.menuService      = menuService;
//        this.userService      = userService;
//        this.placeOrderFacade = placeOrderFacade;
//    }
//
//    /** Display POS order form for a table */
//    @GetMapping("/{tableId}")
//    public String orderPage(@PathVariable Long tableId, Authentication auth, Model model) {
//        User waiter = userService.findByUsername(auth.getName());
//        Order order = orderService.getOrCreateOrder(tableId, waiter);
//
//        model.addAttribute("order", order);
//        model.addAttribute("categories", menuService.getAllCategories());
//        model.addAttribute("allItems", menuService.getAllAvailableItems());
//        model.addAttribute("addItemDto", new AddItemDto());
//        return "order";
//    }
//
//    /** Add item to the order */
//    @PostMapping("/{orderId}/add-item")
//    public String addItem(@PathVariable Long orderId, @ModelAttribute AddItemDto dto,
//                          RedirectAttributes ra) {
//        try {
//            Order order = orderService.addItem(orderId, dto);
//            // Find the table ID via the order
//            ra.addFlashAttribute("success", "Item added.");
//            return "redirect:/order/" + order.getTable().getId();
//        } catch (Exception e) {
//            ra.addFlashAttribute("error", e.getMessage());
//            return "redirect:/tables";
//        }
//    }
//
//    /** Remove (void) a specific item */
//    @PostMapping("/item/{itemId}/remove")
//    public String removeItem(@PathVariable Long itemId,
//                             @RequestParam Long tableId, RedirectAttributes ra) {
//        orderService.removeItem(itemId);
//        ra.addFlashAttribute("success", "Item removed.");
//        return "redirect:/order/" + tableId;
//    }
//
//    /** Send order to kitchen/bar/pizza stations */
//    @PostMapping("/{orderId}/send")
//    public String sendOrder(@PathVariable Long orderId, RedirectAttributes ra) {
//        String error = placeOrderFacade.sendOrder(orderId);
//        if (error != null) {
//            ra.addFlashAttribute("error", error);
//        } else {
//            ra.addFlashAttribute("success", "Order sent to stations!");
//        }
//        Order order = orderService.getOrder(orderId);
//        return "redirect:/order/" + order.getTable().getId();
//    }
//}
