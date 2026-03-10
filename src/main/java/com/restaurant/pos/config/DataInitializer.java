package com.restaurant.pos.config;

import com.restaurant.pos.entity.*;
import com.restaurant.pos.enums.Role;
import com.restaurant.pos.enums.StationType;
//import com.restaurant.pos.patterns.observer.StationEventPublisher;
//import com.restaurant.pos.patterns.observer.WaiterNotifierObserver;
import com.restaurant.pos.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Seeds the database with initial demo data on every startup (only if tables are empty).
 * Also registers observers.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository         userRepository;
    private final TableRepository        tableRepository;
    private final MenuCategoryRepository categoryRepository;
    private final MenuItemRepository     menuItemRepository;
    private final PasswordEncoder        passwordEncoder;
//    private final StationEventPublisher  eventPublisher;
//    private final WaiterNotifierObserver waiterObserver;

    public DataInitializer(
            UserRepository userRepository,
            TableRepository tableRepository,
            MenuCategoryRepository categoryRepository,
            MenuItemRepository menuItemRepository,
            PasswordEncoder passwordEncoder
//            StationEventPublisher eventPublisher,
//            WaiterNotifierObserver waiterObserver
            ) {
        this.userRepository      = userRepository;
        this.tableRepository     = tableRepository;
        this.categoryRepository  = categoryRepository;
        this.menuItemRepository  = menuItemRepository;
        this.passwordEncoder     = passwordEncoder;
        //this.eventPublisher      = eventPublisher;
        //this.waiterObserver      = waiterObserver;
    }

    @Override
    public void run(String... args) {
        // Register Observer
        //eventPublisher.register(waiterObserver);

        seedUsers();
        seedTables();
        seedMenu();
    }

    private void seedUsers() {
        if (userRepository.count() > 0) return;

        List<User> users = Arrays.asList(
                User.builder().username("admin").password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN).fullName("Admin User").active(true).build(),
                User.builder().username("chi_ion").password(passwordEncoder.encode("pass123"))
                        .role(Role.WAITER).fullName("Ion Chi").active(true).build(),
                User.builder().username("chi.maria").password(passwordEncoder.encode("pass123"))
                        .role(Role.WAITER).fullName("Maria Chi").active(true).build(),
                User.builder().username("kitchen1").password(passwordEncoder.encode("pass123"))
                        .role(Role.KITCHEN).fullName("Chef Marco").active(true).build(),
                User.builder().username("bar1").password(passwordEncoder.encode("pass123"))
                        .role(Role.BAR).fullName("Bartender Alex").active(true).build(),
                User.builder().username("pizza1").password(passwordEncoder.encode("pass123"))
                        .role(Role.PIZZA).fullName("Pizza Chef Luca").active(true).build()
        );
        userRepository.saveAll(users);
        System.out.println("[INIT] Users seeded.");
    }

    private void seedTables() {
        if (tableRepository.count() > 0) return;

        for (int i = 1; i <= 12; i++) {
            tableRepository.save(RestaurantTable.builder()
                    .tableNumber(i).capacity(4).occupied(false).build());
        }
        System.out.println("[INIT] 12 tables seeded.");
    }

    private void seedMenu() {
        if (menuItemRepository.count() > 0) return;

        // --- KITCHEN CATEGORY ---
        MenuCategory starters = MenuCategory.builder().name("Starters").build();
        MenuCategory mains    = MenuCategory.builder().name("Main Courses").build();
        categoryRepository.saveAll(Arrays.asList(starters, mains));

        menuItemRepository.saveAll(Arrays.asList(
                MenuItem.builder().name("Caesar Salad").price(new BigDecimal("8.50")).stationType(StationType.KITCHEN).category(starters).available(true).build(),
                MenuItem.builder().name("Tomato Soup").price(new BigDecimal("6.00")).stationType(StationType.KITCHEN).category(starters).available(true).build(),
                MenuItem.builder().name("Grilled Chicken").price(new BigDecimal("16.50")).stationType(StationType.KITCHEN).category(mains).available(true).build(),
                MenuItem.builder().name("Beef Burger").price(new BigDecimal("14.00")).stationType(StationType.KITCHEN).category(mains).available(true).build(),
                MenuItem.builder().name("Pasta Carbonara").price(new BigDecimal("13.00")).stationType(StationType.KITCHEN).category(mains).available(true).build()
        ));

        // --- BAR CATEGORY ---
        MenuCategory drinks = MenuCategory.builder().name("Drinks").build();
        categoryRepository.save(drinks);

        menuItemRepository.saveAll(Arrays.asList(
                MenuItem.builder().name("Coca-Cola").price(new BigDecimal("3.00")).stationType(StationType.BAR).category(drinks).available(true).build(),
                MenuItem.builder().name("Beer 0.5L").price(new BigDecimal("5.00")).stationType(StationType.BAR).category(drinks).available(true).build(),
                MenuItem.builder().name("Red Wine (Glass)").price(new BigDecimal("7.00")).stationType(StationType.BAR).category(drinks).available(true).build(),
                MenuItem.builder().name("Espresso").price(new BigDecimal("2.50")).stationType(StationType.BAR).category(drinks).available(true).build(),
                MenuItem.builder().name("Orange Juice").price(new BigDecimal("4.00")).stationType(StationType.BAR).category(drinks).available(true).build()
        ));

        // --- PIZZA CATEGORY ---
        MenuCategory pizzas = MenuCategory.builder().name("Pizzas").build();
        categoryRepository.save(pizzas);

        menuItemRepository.saveAll(Arrays.asList(
                MenuItem.builder().name("Margherita").price(new BigDecimal("10.00")).stationType(StationType.PIZZA).category(pizzas).available(true).build(),
                MenuItem.builder().name("Pepperoni").price(new BigDecimal("12.50")).stationType(StationType.PIZZA).category(pizzas).available(true).build(),
                MenuItem.builder().name("Four Cheese").price(new BigDecimal("13.00")).stationType(StationType.PIZZA).category(pizzas).available(true).build(),
                MenuItem.builder().name("Veggie Supreme").price(new BigDecimal("11.50")).stationType(StationType.PIZZA).category(pizzas).available(true).build()
        ));

        System.out.println("[INIT] Menu seeded.");
    }
}
