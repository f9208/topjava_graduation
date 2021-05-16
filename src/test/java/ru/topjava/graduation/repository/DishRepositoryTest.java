package ru.topjava.graduation.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.topjava.graduation.web.Exceptions.NotFoundException;
import ru.topjava.graduation.model.entities.Dish;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.topjava.graduation.repository.testData.DishTestData.*;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.*;


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
        dishMatcher.assertMatch(MUFFIN, dishRepository.get(MUFFIN_ID, MEAT_HOME_ID));
    }

    @Test
    void getActualMenu() {
        dishMatcher.assertMatch(List.of(FISH, POTATO), dishRepository.getActualMenu(MEAT_HOME_ID));
    }

    @Test
    void getFullMenu() {
        dishMatcher.assertMatch(List.of(FISH, MUFFIN, POTATO, SOUP), dishRepository.getFullMenu(MEAT_HOME_ID));
    }

    @Test
    void getNotOwner() {
        assertThrows(NotFoundException.class, () -> dishRepository.get(MUFFIN_ID, BEAR_GRIZZLY_ID));
    }

    @Test
    void delete() {
        dishRepository.delete(FISH_ID, MEAT_HOME_ID);
        assertThrows(NotFoundException.class, () -> dishRepository.get(FISH_ID, MEAT_HOME_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> dishRepository.delete(1, BEAR_GRIZZLY_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> dishRepository.delete(POTATO_ID, BEAR_GRIZZLY_ID));
    }

    @Test
    void setDishEnabled() {
        assertFalse(dishRepository.get(SOUP_ID, MEAT_HOME_ID).isEnabled());
        dishRepository.setVisibility(SOUP_ID, MEAT_HOME_ID, true);
        assertTrue(dishRepository.get(SOUP_ID, MEAT_HOME_ID).isEnabled());
        dishRepository.setVisibility(SOUP_ID, MEAT_HOME_ID, false);
        assertFalse(dishRepository.get(SOUP_ID, MEAT_HOME_ID).isEnabled());
    }

    @Test
    void setDishDisabled() {
        assertTrue(dishRepository.get(POTATO_ID, MEAT_HOME_ID).isEnabled());
        dishRepository.setVisibility(POTATO_ID, MEAT_HOME_ID, false);
        assertFalse(dishRepository.get(POTATO_ID, MEAT_HOME_ID).isEnabled());
        dishRepository.setVisibility(POTATO_ID, MEAT_HOME_ID, true);
        assertTrue(dishRepository.get(POTATO_ID, MEAT_HOME_ID).isEnabled());
    }

    @Test
    void setVisibilityNotOwner() {
        assertThrows(NotFoundException.class, () -> dishRepository.setVisibility(POTATO_ID, BEAR_GRIZZLY_ID, false));
    }

    @Test
    void createDuplicate() {
        assertThrows(DataAccessException.class,
                () -> dishRepository.create(new Dish(null, "fish", 123, meatHome, LocalDateTime.now(), true), MEAT_HOME_ID));
    }
}