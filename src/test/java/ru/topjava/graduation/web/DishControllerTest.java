package ru.topjava.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduation.TestUtil;
import ru.f9208.choiserestaurant.model.entities.Dish;
import ru.f9208.choiserestaurant.repository.DishRepository;
import ru.f9208.choiserestaurant.web.Exceptions.NotFoundException;
import ru.f9208.choiserestaurant.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduation.TestUtil.userHttpBasic;
import static ru.topjava.graduation.repository.testData.DishTestData.*;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.BEAR_GRIZZLY_ID;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.MEAT_HOME_ID;
import static ru.topjava.graduation.repository.testData.UserTestData.admin;
import static ru.topjava.graduation.repository.testData.UserTestData.userJonny;
import static ru.f9208.choiserestaurant.web.RestaurantsController.RESTAURANTS;

public class DishControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = RESTAURANTS + "/";
    private static final String MENU = "/menu/";

    @Autowired
    DishRepository dishRepository;

    @Test
    void getMenuUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + MENU))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(List.of(FISH, POTATO)));
    }

    @Test
    void getFullMenuUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + MENU + "full"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getFullMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + MENU + "full")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(MEAT_HOME_FULL_MENU));
    }

    @Test
    void getFullMenuIsForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + MENU + "full")
                .with(userHttpBasic(userJonny)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void getOneDish() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + MENU + POTATO_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(POTATO));
    }

    @Test
    void getOneDishNoAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + MENU + POTATO_ID))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void addDish() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + MEAT_HOME_ID + MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = TestUtil.readFromJson(action, Dish.class);
        int id = created.getId();
        DISH_MATCHER.assertMatch(created, dishRepository.get(id, MEAT_HOME_ID));
    }

    @Test
    void addDishIsForbidden() throws Exception {
        Dish newDish = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL + MEAT_HOME_ID + MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish))
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void updateDish() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + BEAR_GRIZZLY_ID + MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(updated, dishRepository.get(PASTA_ID, BEAR_GRIZZLY_ID));
    }

    @Test
    void updateDishIsForbidden() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + BEAR_GRIZZLY_ID + MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(userJonny)))
                .andExpect(status().isForbidden());
        DISH_MATCHER.assertMatch(PASTA, dishRepository.get(PASTA_ID, BEAR_GRIZZLY_ID));
    }

    @Test
    void deleteDish() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAT_HOME_ID + MENU)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(SOUP_ID))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishRepository.get(SOUP_ID, MEAT_HOME_ID));
    }

    @Test
    void deleteDishIsForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAT_HOME_ID + MENU)
                .content(String.valueOf(SOUP_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isForbidden());
        DISH_MATCHER.assertMatch(SOUP, dishRepository.get(SOUP_ID, MEAT_HOME_ID));
    }

    @Test
    void disable() throws Exception {
        assertTrue(dishRepository.get(FISH_ID, MEAT_HOME_ID).isEnabled());
        perform(MockMvcRequestBuilders.patch(REST_URL + MEAT_HOME_ID + MENU + FISH_ID)
                .param("enabled", "false")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.get(FISH_ID, MEAT_HOME_ID).isEnabled());
    }

    @Test
    void enable() throws Exception {
        assertFalse(dishRepository.get(SOUP_ID, MEAT_HOME_ID).isEnabled());
        perform(MockMvcRequestBuilders.patch(REST_URL + MEAT_HOME_ID + MENU + SOUP_ID)
                .param("enabled", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertTrue(dishRepository.get(SOUP_ID, MEAT_HOME_ID).isEnabled());
    }

    @Test
    void enableIsForbidden() throws Exception {
        assertFalse(dishRepository.get(SOUP_ID, MEAT_HOME_ID).isEnabled());
        perform(MockMvcRequestBuilders.patch(REST_URL + MEAT_HOME_ID + MENU + SOUP_ID)
                .param("enabled", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isForbidden());
        assertFalse(dishRepository.get(SOUP_ID, MEAT_HOME_ID).isEnabled());
    }
}