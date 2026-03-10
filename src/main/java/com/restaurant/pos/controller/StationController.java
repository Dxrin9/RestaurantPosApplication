package com.restaurant.pos.controller;

import com.restaurant.pos.enums.StationType;
import com.restaurant.pos.service.StationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/station")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/kitchen")
    public String kitchenStation(Model model) {
        model.addAttribute("items", stationService.getPendingItems(StationType.KITCHEN));
        model.addAttribute("station", "KITCHEN");
        return "station_kitchen";
    }

    @GetMapping("/bar")
    public String barStation(Model model) {
        model.addAttribute("items", stationService.getPendingItems(StationType.BAR));
        model.addAttribute("station", "BAR");
        return "station_bar";
    }

    @GetMapping("/pizza")
    public String pizzaStation(Model model) {
        model.addAttribute("items", stationService.getPendingItems(StationType.PIZZA));
        model.addAttribute("station", "PIZZA");
        return "station_pizza";
    }

    /** Mark a specific item as DONE from any station */
    @PostMapping("/item/{itemId}/done")
    public String markDone(@PathVariable Long itemId,
                           @RequestParam String stationPath,
                           RedirectAttributes ra) {
        stationService.markItemReady(itemId);
        ra.addFlashAttribute("success", "Item marked as ready!");
        return "redirect:/station/" + stationPath;
    }
}
