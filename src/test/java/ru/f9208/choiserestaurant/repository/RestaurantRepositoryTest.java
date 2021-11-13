package ru.f9208.choiserestaurant.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.web.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.f9208.choiserestaurant.model.entities.AbstractNamedEntity.START_SEQ;
import static ru.f9208.choiserestaurant.repository.testData.DishTestData.DISH_MATCHER;
import static ru.f9208.choiserestaurant.repository.testData.RestaurantTestData.*;
import static ru.f9208.choiserestaurant.repository.testData.VoteTestData.*;


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

    @Test
    void save() {
        Restaurant original = new Restaurant(teaHome);
        Restaurant saved = restaurantRepository.save(teaHome);
        original.setId(saved.getId());
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getOne(saved.getId()), original);
    }

    @Test
    void getWithMenu() {
        Restaurant indm = restaurantRepository.getWithMenu(MEAT_HOME_ID);
        RESTAURANT_MATCHER.assertMatch(
                indm, meatHome);
        DISH_MATCHER.assertMatch(indm.getMenu(), meatHome.getMenu());
    }

    @Test
    void
    getAllWithVotesBetween() {
        List<Restaurant> restaurants = restaurantRepository.getAllWithVotesBetween(LocalDate.now(), LocalDate.now());
        RESTAURANT_MATCHER.assertMatch(restaurants, meatHome, bearGrizzly);
        VOTE_TEST_MATCHER.assertMatch(restaurants.get(MEAT_HOME_ID-START_SEQ).getVote(), VOTE14_TODAY, VOTE15_TODAY);
        VOTE_TEST_MATCHER.assertMatch(restaurants.get(BEAR_GRIZZLY_ID-START_SEQ).getVote(), VOTE13_TODAY);
    }
}