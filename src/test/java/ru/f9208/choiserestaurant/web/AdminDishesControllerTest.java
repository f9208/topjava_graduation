package ru.f9208.choiserestaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.f9208.choiserestaurant.TestUtil;
import ru.f9208.choiserestaurant.model.entities.Dish;
import ru.f9208.choiserestaurant.repository.DishRepository;
import ru.f9208.choiserestaurant.repository.testData.RestaurantTestData;
import ru.f9208.choiserestaurant.repository.testData.UserTestData;
import ru.f9208.choiserestaurant.web.exceptions.NotFoundException;
import ru.f9208.choiserestaurant.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.f9208.choiserestaurant.repository.testData.DishTestData.*;
import static ru.f9208.choiserestaurant.repository.testData.RestaurantTestData.BEAR_GRIZZLY_ID;
import static ru.f9208.choiserestaurant.repository.testData.RestaurantTestData.MEAT_HOME_ID;
import static ru.f9208.choiserestaurant.web.AdminRestaurantController.ADMIN_RESTAURANT;
import static ru.f9208.choiserestaurant.web.DishController.DISHES;

class AdminDishesControllerTest extends AbstractRestControllerTest {
    public static final String REST_URL = ADMIN_RESTAURANT + "/";
    @Autowired
    DishRepository dishRepository;

    @Test
    void addDish() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + MEAT_HOME_ID + DISHES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish))
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = TestUtil.readFromJson(action, Dish.class);
        int id = created.getId();
        DISH_MATCHER.assertMatch(created, dishRepository.get(id, MEAT_HOME_ID));
    }

    @Test
    void addDishIsForbidden() throws Exception {
        Dish newDish = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL + MEAT_HOME_ID + DISHES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish))
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void updateDish() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + BEAR_GRIZZLY_ID + DISHES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(updated, dishRepository.get(PASTA_ID, BEAR_GRIZZLY_ID));
    }

    @Test
    void updateDishIsForbidden() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + BEAR_GRIZZLY_ID + DISHES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andExpect(status().isForbidden());
        DISH_MATCHER.assertMatch(PASTA, dishRepository.get(PASTA_ID, BEAR_GRIZZLY_ID));
    }

    @Test
    void updateDishIsNoConsistent() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MEAT_HOME_ID + DISHES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isUnprocessableEntity());
        DISH_MATCHER.assertMatch(PASTA, dishRepository.get(PASTA_ID, BEAR_GRIZZLY_ID));
    }

    @Test
    void deleteDish() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAT_HOME_ID + DISHES + "/" + SOUP_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(SOUP_ID))
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishRepository.get(SOUP_ID, MEAT_HOME_ID));
    }

    @Test
    void deleteDishIsForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAT_HOME_ID + DISHES + "/" + SOUP_ID)
                .content(String.valueOf(SOUP_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isForbidden());
        DISH_MATCHER.assertMatch(SOUP, dishRepository.get(SOUP_ID, MEAT_HOME_ID));
    }
}