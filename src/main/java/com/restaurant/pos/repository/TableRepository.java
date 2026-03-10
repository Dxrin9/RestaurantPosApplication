package com.restaurant.pos.repository;

import com.restaurant.pos.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableRepository extends JpaRepository<RestaurantTable, Long> {
    List<RestaurantTable> findAllByOrderByTableNumberAsc();
    List<RestaurantTable> findByOccupied(boolean occupied);
}
