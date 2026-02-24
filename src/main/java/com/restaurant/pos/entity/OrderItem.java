package com.restaurant.pos.entity;

import com.restaurant.pos.enums.StationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Represents a single line item in an Order.
 *
 * DESIGN PATTERN: Flyweight (Pattern #19) - extrinsic state
 * The quantity, notes, and status here are the EXTRINSIC state.
 * The intrinsic state (name, price, type) lives in MenuItem.
 *
 * DESIGN PATTERN: Decorator (Pattern #13)
 * Extra toppings or ingredients are modelled as decorators applied on top of an OrderItem.
 * See patterns/decorator/ package.
 */
@Entity
@Table(name = "order_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column(nullable = false)
    private Integer quantity;

    /** Unit price at time of ordering (snapshot) */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    /** Special instructions from waiter */
    private String notes;

    /** Extra toppings/ingredients string (set by Decorator pattern) */
    private String extras;

    /** Routed station type (copied from MenuItem at order time) */
    @Enumerated(EnumType.STRING)
    private StationType stationType;

    /** true = voided/removed by admin */
    private boolean voided = false;

    /** true = station marked this item as ready */
    private boolean ready = false;

    /** Line total = quantity × unitPrice */
    @Transient
    public BigDecimal getLineTotal() {
        if (unitPrice == null || quantity == null) return BigDecimal.ZERO;
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
