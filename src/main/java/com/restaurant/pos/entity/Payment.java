package com.restaurant.pos.entity;

import com.restaurant.pos.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a completed payment for an order.
 * Stored in DB for reporting purposes.
 */
@Entity
@Table(name = "payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    /** Discount applied: fixed amount */
    @Column(precision = 10, scale = 2)
    private BigDecimal discountAmount;

    /** Final amount paid after discount */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal finalAmount;

    private LocalDateTime paidAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id")
    private User waiter;

    @PrePersist
    public void prePersist() {
        this.paidAt = LocalDateTime.now();
    }
}
