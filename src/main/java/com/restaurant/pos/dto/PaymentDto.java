package com.restaurant.pos.dto;

import com.restaurant.pos.enums.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

/** Data Transfer Object for the payment form submission */
@Data
public class PaymentDto {
    private Long orderId;
    private PaymentMethod method;
    private BigDecimal discountAmount;
}
