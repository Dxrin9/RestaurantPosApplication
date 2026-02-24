package com.restaurant.pos.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a physical dining table in the restaurant.
 */
@Entity
@Table(name = "restaurant_tables")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer tableNumber;

    private Integer capacity;

    /** true = table currently has an open order */
    private boolean occupied = false;
}
