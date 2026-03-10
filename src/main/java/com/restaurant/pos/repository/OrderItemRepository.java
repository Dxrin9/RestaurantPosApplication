package com.restaurant.pos.repository;

import com.restaurant.pos.entity.OrderItem;
import com.restaurant.pos.enums.StationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT oi FROM OrderItem oi JOIN oi.order o WHERE oi.stationType = :stationType AND o.status = 'SENT' AND oi.voided = false")
    List<OrderItem> findPendingItemsByStation(@Param("stationType") StationType stationType);
}
