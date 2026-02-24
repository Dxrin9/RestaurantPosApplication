package com.restaurant.pos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a category of menu items (e.g., Starters, Mains, Drinks, Pizza).
 *
 * DESIGN PATTERN: Composite Pattern (Pattern #12)
 * A MenuCategory can contain MenuItems (leaves) and sub-categories (composites).
 * Both implement a common interface allowing uniform treatment.
 */
@Entity
@Table(name = "menu_categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    /** Optional parent for subcategory support (Composite pattern) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MenuCategory parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MenuCategory> subCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MenuItem> items = new ArrayList<>();
}
