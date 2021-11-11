package ru.f9208.choiserestaurant.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.f9208.choiserestaurant.model.entities.Dish;
import ru.f9208.choiserestaurant.repository.testData.RestaurantTestData;
import ru.f9208.choiserestaurant.web.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.f9208.choiserestaurant.repository.testData.DishTestData.*;
import static ru.f9208.choiserestaurant.repository.testData.RestaurantTestData.*;


class DishRepositoryTest extends AbstractStarterTest {
    @Autowired
    DishRepository dishRepository;

    TestMatcher<Dish> dishMatcher = DISH_MATCHER;

    @BeforeAll
    static void init() {
       bearGrizzly.getName();
    }

    @Test
    void create() {
        Dish created = dishRepository.create(getNew(), MEAT_HOME_ID);
        int dishId = created.getId();
        Dish newDish = getNew();
        newDish.setId(dishId);
        dishMatcher.assertMatch(created, newDish);
        dishMatcher.assertMatch(dishRepository.get(newDish.getId(), MEAT_HOME_ID), newDish);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        dishRepository.update(updated, BEAR_GRIZZLY_ID);
        dishMatcher.assertMatch(dishRepository.get(PASTA_ID, BEAR_GRIZZLY_ID), getUpdated());
    }

    @Test
    void updateNotOwn() {
        Dish updated = getUpdated();
        assertThrows(NotFoundException.class, () -> dishRepository.update(updated, MEAT_HOME_ID));
    }

    @Test
    void get() {
        dishMatcher.assertMatch(MUFFIN, dishRepository.get(MUFFIN_ID,MEAT_HOME_ID));
    }

    @Test
    void getMenu() {
        dishMatcher.assertMatch(List.of(FISH, MUFFIN, POTATO, SOUP), dishRepository.getMenu(RestaurantTestData.MEAT_HOME_ID));
    }

    @Test
    void getNotOwner() {
        assertThrows(NotFoundException.class, () -> dishRepository.get(MUFFIN_ID,BEAR_GRIZZLY_ID));
    }

    @Test
    void delete() {
        dishRepository.delete(FISH_ID,MEAT_HOME_ID);
        assertThrows(NotFoundException.class, () -> dishRepository.get(FISH_ID,MEAT_HOME_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> dishRepository.delete(1,BEAR_GRIZZLY_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> dishRepository.delete(POTATO_ID,BEAR_GRIZZLY_ID));
    }
    @Test
    void createDuplicate() {
        assertThrows(DataAccessException.class,
                () -> dishRepository.create(new Dish(null, "fish", 123,meatHome, LocalDate.now()),MEAT_HOME_ID));
    }
}