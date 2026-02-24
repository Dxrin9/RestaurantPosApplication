package com.restaurant.pos.entity;

import com.restaurant.pos.enums.StationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Represents a single item on the restaurant menu.
 *
 * DESIGN PATTERN: Flyweight Pattern (Pattern #19)
 * MenuItem holds the INTRINSIC (shared, immutable) state: name, price, stationType, category.
 * The EXTRINSIC (per-order) state like quantity and notes lives in OrderItem.
 * All order items for the same menu item share this single entity — no duplication of menu data.
 */
@Entity
@Table(name = "menu_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /** Determines which station prepares this item */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StationType stationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private MenuCategory category;

    private boolean available = true;
}
