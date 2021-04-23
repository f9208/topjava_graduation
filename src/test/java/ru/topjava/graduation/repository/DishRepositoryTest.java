package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.graduation.model.entities.Dish;

import java.time.LocalDateTime;

class DishRepositoryTest extends AbstractStarterTest {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    DishRepository dishRepository;

    @Test
    void create() {
        Dish dish = new Dish(null, "pancake", 50, LocalDateTime.now());
        System.out.println(dishRepository.save(dish, 10003));
    }

    @Test
    void get() {
        Integer id = 10004;
        Integer restaurant_id = 10002;
        System.out.println(dishRepository.get(id, restaurant_id));
    }

    @Test
    void getAll() {
        Integer restaurant_id = 10002;
        System.out.println(dishRepository.getActualMenu(restaurant_id));
    }

    @Test
    void delete() {
        dishRepository.delete(10005, 10002);
    }

    @Test
    void setDishEnabled() {
        dishRepository.setVisibility(10004, 10002, true);
    }

    @Test
    void setDishDisabled() {
        dishRepository.setVisibility(10004, 10002, false);
    }
}