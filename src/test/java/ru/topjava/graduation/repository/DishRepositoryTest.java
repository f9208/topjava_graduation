package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.graduation.model.entities.Dish;

class DishRepositoryTest extends AbstractStarterTest {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    DishRepository dishRepository;

    @Test
    void save() {
    }

    @Test
    void create() {
        Dish dish = new Dish(null, "pancake", 50);
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
        System.out.println(dishRepository.getAllByRestaurantId(restaurant_id));
    }

    @Test
    void delete() {
        dishRepository.delete(10005, 10002);
    }
}