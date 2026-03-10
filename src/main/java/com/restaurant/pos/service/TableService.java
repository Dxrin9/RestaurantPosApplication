package com.restaurant.pos.service;

import com.restaurant.pos.entity.RestaurantTable;
import com.restaurant.pos.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<RestaurantTable> getAllTables() {
        return tableRepository.findAllByOrderByTableNumberAsc();
    }

    public RestaurantTable getTable(Long id) {
        return tableRepository.findById(id).orElseThrow();
    }
}
