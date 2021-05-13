package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.graduation.Exceptions.NotFoundException;
import ru.topjava.graduation.model.entities.Restaurant;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.*;


class RestaurantRepositoryTest extends AbstractStarterTest {
    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    void getAll() {
        RESTAURANT_MATCHER.assertMatch(List.of(bearGrizzly, meatHome), restaurantRepository.getAll());
    }

    @Test
    void getOne() {
        RESTAURANT_MATCHER.assertMatch(meatHome, restaurantRepository.getOne(MEAT_HOME_ID));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantRepository.getOne(NOT_FOUND));
    }

    @Test
    void create() {
        Restaurant created = restaurantRepository.create(getNewRestaurant());
        int createdId = created.getId();
        Restaurant newRestaurant = getNewRestaurant();
        newRestaurant.setId(createdId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getOne(createdId), newRestaurant);
    }

    @Test
    void update() {
        Restaurant updated = getUpdatedRestaurant();
        restaurantRepository.update(updated);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getOne(BEAR_GRIZZLY_ID), getUpdatedRestaurant());
    }

    @Test
    void delete() {
        restaurantRepository.delete(MEAT_HOME_ID);
        assertThrows(NotFoundException.class, () -> restaurantRepository.getOne(MEAT_HOME_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantRepository.delete(NOT_FOUND));
    }

    @Test
    void getAllWithVotes() {
        System.out.println(restaurantRepository.getAllWithVotes().get(1).getVote());
    }
}