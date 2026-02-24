package com.restaurant.pos.entity;

import com.restaurant.pos.enums.Role;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a restaurant employee (waiter, kitchen staff, admin, etc.)
 */
@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Username in lowercase (e.g. chi_ion, chi.maria) */
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String fullName;

    private boolean active = true;
}
