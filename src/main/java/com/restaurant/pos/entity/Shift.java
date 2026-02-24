package com.restaurant.pos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents an employee work shift (time clock entry).
 */
@Entity
@Table(name = "shifts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Date of this shift */
    private LocalDate shiftDate;

    /** Time when employee clocked in */
    private LocalDateTime startTime;

    /** Time when employee clocked out (null if still working) */
    private LocalDateTime endTime;

    /** Total worked minutes (calculated when shift ends) */
    private Integer workedMinutes;
}
