package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.graduation.model.entities.Restaurant;

class RestaurantRepositoryTest extends AbstractStarterTest {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    DishRepository dishRepository;

    @Test
    void getAll() {
        System.out.println(restaurantRepository.getAll());
    }

    @Test
    void getOne() {
        Restaurant r = restaurantRepository.get(10002);
    }

    @Test
    void getWithDish() {
        Restaurant r = restaurantRepository.getWithDish(10002);
        System.out.println(r.getMenu());
    }
}