package com.restaurant.pos.service;

import com.restaurant.pos.entity.MenuCategory;
import com.restaurant.pos.entity.MenuItem;
import com.restaurant.pos.repository.MenuCategoryRepository;
import com.restaurant.pos.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuCategoryRepository categoryRepository;
    private final MenuItemRepository     itemRepository;

    public MenuService(MenuCategoryRepository categoryRepository, MenuItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository     = itemRepository;
    }

    public List<MenuCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<MenuCategory> getRootCategories() {
        return categoryRepository.findByParentIsNull();
    }

    public MenuCategory getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public List<MenuItem> getItemsByCategory(Long categoryId) {
        MenuCategory cat = categoryRepository.findById(categoryId).orElseThrow();
        return itemRepository.findByCategoryAndAvailableTrue(cat);
    }

    public MenuItem getItem(Long id) {
        return itemRepository.findById(id).orElseThrow();
    }

    public List<MenuItem> getAllAvailableItems() {
        return itemRepository.findByAvailableTrue();
    }
}
