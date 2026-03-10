//package com.restaurant.pos.controller;
//
//import com.restaurant.pos.service.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    private final ShiftService  shiftService;
//    private final OrderService  orderService;
//    private final ReportService reportService;
//
//    public AdminController(ShiftService shiftService, OrderService orderService, ReportService reportService) {
//        this.shiftService  = shiftService;
//        this.orderService  = orderService;
//        this.reportService = reportService;
//    }
//
//    @GetMapping("/live-shifts")
//    public String liveShifts(Model model) {
//        model.addAttribute("activeShifts", shiftService.getAllActiveShifts());
//        return "admin_live";
//    }
//
//    @GetMapping("/reports/shifts")
//    public String dailyReport(Model model) {
//        model.addAttribute("report", reportService.generateDailyReport());
//        model.addAttribute("shifts", shiftService.getTodayShifts());
//        model.addAttribute("orders", orderService.getAllOrders());
//        return "admin_reports";
//    }
//
//    @GetMapping("/orders")
//    public String allOrders(Model model) {
//        model.addAttribute("orders", orderService.getAllOrders());
//        return "admin_orders";
//    }
//
//    @PostMapping("/orders/{orderId}/cancel")
//    public String cancelOrder(@PathVariable Long orderId, RedirectAttributes ra) {
//        orderService.cancelOrder(orderId);
//        ra.addFlashAttribute("success", "Order cancelled.");
//        return "redirect:/admin/orders";
//    }
//
//    @PostMapping("/items/{itemId}/void")
//    public String voidItem(@PathVariable Long itemId, RedirectAttributes ra) {
//        orderService.removeItem(itemId);
//        ra.addFlashAttribute("success", "Item voided.");
//        return "redirect:/admin/orders";
//    }
//}
