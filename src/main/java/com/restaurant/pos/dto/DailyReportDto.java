package com.restaurant.pos.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/** DTO for the admin daily report page */
@Data
public class DailyReportDto {
    private int totalOrders;
    private BigDecimal totalSales;
    private BigDecimal totalDiscounts;
    private int voidedItems;
    /** Map of employee username → worked minutes */
    private Map<String, Integer> workedMinutesPerEmployee;
}
