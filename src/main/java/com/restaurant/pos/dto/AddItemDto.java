package com.restaurant.pos.dto;

import lombok.Data;

/** DTO for adding an item to an order from the POS form */
@Data
public class AddItemDto {
    private Long menuItemId;
    private Integer quantity;
    private String notes;
    private String extras; // for pizza toppings
}
