package ru.topjava.graduation.web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduation.Exceptions.NotFoundException;
import ru.topjava.graduation.TestUtil;
import ru.topjava.graduation.model.entities.Dish;
import ru.topjava.graduation.repository.DishRepository;
import ru.topjava.graduation.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduation.repository.testData.DishTestData.*;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.*;
import static ru.topjava.graduation.web.RestaurantsController.RESTAURANTS;

public class DishControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = RESTAURANTS + "/";
    @Autowired
    DishRepository dishRepository;

    @BeforeAll
    static void init() {
        bearGrizzly.getName();
    }

    @Test
    void getMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + "/menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(List.of(FISH, POTATO)));
    }

    @Test
    void getFullMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + "/menu/full"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(MEAT_HOME_FULL_MENU));
    }

    @Test
    void getOneDish() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + "/menu/" + POTATO_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(POTATO));
    }

    @Test
    void addDish() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + MEAT_HOME_ID + "/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = TestUtil.readFromJson(action, Dish.class);
        int id = created.getId();
        DISH_MATCHER.assertMatch(created, dishRepository.get(id, MEAT_HOME_ID));
    }

    @Test
    void updateDish() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + BEAR_GRIZZLY_ID + "/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(updated, dishRepository.get(PASTA_ID, BEAR_GRIZZLY_ID));
    }

    @Test
    void deleteDish() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAT_HOME_ID + "/menu" + "/" + SOUP_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishRepository.get(SOUP_ID, MEAT_HOME_ID));
    }

    @Test
    void disable() throws Exception {
        assertTrue(dishRepository.get(FISH_ID, MEAT_HOME_ID).isEnabled());
        perform(MockMvcRequestBuilders.patch(REST_URL + MEAT_HOME_ID + "/menu/" + FISH_ID)
                .param("enabled", "false")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.get(FISH_ID, MEAT_HOME_ID).isEnabled());
    }

    @Test
    void enable() throws Exception {
        assertFalse(dishRepository.get(SOUP_ID, MEAT_HOME_ID).isEnabled());
        perform(MockMvcRequestBuilders.patch(REST_URL + MEAT_HOME_ID + "/menu/" + SOUP_ID)
                .param("enabled", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertTrue(dishRepository.get(SOUP_ID, MEAT_HOME_ID).isEnabled());
    }
}