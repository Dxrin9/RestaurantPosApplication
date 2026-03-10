package com.restaurant.pos.patterns.singleton;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ============================================================
 * DESIGN PATTERN #1: SINGLETON
 * ============================================================
 * Ensures only ONE instance of the order number generator exists
 * in the entire application. Uses Spring @Component (which is a singleton
 * by default) plus an AtomicInteger for thread-safe sequential numbering.
 *
 * Usage: Injected into OrderService to generate unique order numbers
 * like "ORD-2024-01-15-001", "ORD-2024-01-15-002", etc.
 */
@Component
public class OrderNumberGenerator {

    // Thread-safe counter — AtomicInteger prevents race conditions
    private final AtomicInteger dailyCounter = new AtomicInteger(0);
    private LocalDate currentDate = LocalDate.now();

    /**
     * Generates the next unique order number.
     * Format: ORD-YYYY-MM-DD-NNN
     * Resets daily counter when the date changes.
     */
    public synchronized String next() {
        LocalDate today = LocalDate.now();

        // Reset counter each new day
        if (!today.equals(currentDate)) {
            currentDate = today;
            dailyCounter.set(0);
        }

        int count = dailyCounter.incrementAndGet();
        String dateStr = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return String.format("ORD-%s-%03d", dateStr, count);
    }
}
