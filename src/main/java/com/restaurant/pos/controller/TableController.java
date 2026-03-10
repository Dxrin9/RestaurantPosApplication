package com.restaurant.pos.controller;

import com.restaurant.pos.service.TableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/tables")
    public String tablesPage(Model model) {
        model.addAttribute("tables", tableService.getAllTables());
        return "tables";
    }
}
