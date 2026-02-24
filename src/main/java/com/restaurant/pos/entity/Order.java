package com.restaurant.pos.entity;

import com.restaurant.pos.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer order at a table.
 *
 * DESIGN PATTERN: State Pattern (Pattern #16)
 * The 'status' field drives the order's lifecycle.
 * The order transitions: NEW → SENT → READY → PAID (or CANCELLED).
 * Business logic in OrderService enforces valid state transitions.
 */
@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Human-readable order number from Singleton OrderNumberGenerator */
    @Column(unique = true)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private RestaurantTable table;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id")
    private User waiter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.NEW;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    /** Notes for the entire order */
    private String notes;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /** Calculate total from all non-voided items */
    @Transient
    public BigDecimal getTotal() {
        return items.stream()
                .filter(item -> !item.isVoided())
                .map(OrderItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
