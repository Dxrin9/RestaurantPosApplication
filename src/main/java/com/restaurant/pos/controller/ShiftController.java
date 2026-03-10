package com.restaurant.pos.controller;

import com.restaurant.pos.entity.Shift;
import com.restaurant.pos.entity.User;
import com.restaurant.pos.service.ShiftService;
import com.restaurant.pos.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/shift")
public class ShiftController {

    private final ShiftService shiftService;
    private final UserService  userService;

    public ShiftController(ShiftService shiftService, UserService userService) {
        this.shiftService = shiftService;
        this.userService  = userService;
    }

    @GetMapping
    public String shiftPage(Authentication auth, Model model) {
        User user = userService.findByUsername(auth.getName());
        Optional<Shift> activeShift = shiftService.getActiveShift(user);

        model.addAttribute("user", user);
        model.addAttribute("activeShift", activeShift.orElse(null));
        model.addAttribute("hasActiveShift", activeShift.isPresent());
        return "shift";
    }

    @PostMapping("/start")
    public String startShift(Authentication auth, RedirectAttributes ra) {
        try {
            User user = userService.findByUsername(auth.getName());
            shiftService.startShift(user);
            ra.addFlashAttribute("success", "Shift started successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/shift";
    }

    @PostMapping("/end")
    public String endShift(Authentication auth, RedirectAttributes ra) {
        try {
            User user = userService.findByUsername(auth.getName());
            Shift ended = shiftService.endShift(user);
            ra.addFlashAttribute("success",
                "Shift ended. Worked: " + ended.getWorkedMinutes() + " minutes.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/shift";
    }
}
