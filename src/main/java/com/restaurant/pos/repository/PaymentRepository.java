package com.restaurant.pos.repository;

import com.restaurant.pos.entity.Order;
import com.restaurant.pos.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder(Order order);
    List<Payment> findByPaidAtBetween(LocalDateTime start, LocalDateTime end);
}
