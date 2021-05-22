package ru.topjava.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduation.TestUtil;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.web.Exceptions.NotFoundException;
import ru.f9208.choiserestaurant.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduation.TestUtil.userHttpBasic;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.*;
import static ru.topjava.graduation.repository.testData.UserTestData.admin;
import static ru.topjava.graduation.repository.testData.UserTestData.userJonny;
import static ru.topjava.graduation.repository.testData.VoteTestData.*;
import static ru.f9208.choiserestaurant.web.RestaurantsController.RESTAURANTS;

class RestaurantsControllerTest extends AbstractRestControllerTest {
    @Autowired
    RestaurantRepository restaurantRepository;

    private final String REST_URL = RESTAURANTS + "/";

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RESTAURANT_MATCHER.contentJson(List.of(meatHome, bearGrizzly)));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RESTAURANT_MATCHER.contentJson(List.of(meatHome)));
    }

    @Test
    void getAllWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "with-menu")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_MATCHER_WITHOUT_VOTE.contentJson(List.of(bearGrizzly, meatHome)));
    }

    @Test
    void getRestaurantWithOutMenu() throws Exception {
        meatHome.setMenu(null);
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RESTAURANT_MATCHER.contentJson(meatHome));
    }

    @Test
    void getRestaurantWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "with-menu/" + MEAT_HOME_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RESTAURANT_MATCHER_WITHOUT_VOTE.contentJson(meatHome));
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = getNewRestaurant();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isCreated());
        Restaurant created = TestUtil.readFromJson(actions, Restaurant.class);
        RESTAURANT_MATCHER.assertMatch(created, restaurantRepository.getOne(created.getId()));
    }

    @Test
    void createIsForbidden() throws Exception {
        Restaurant newRestaurant = getNewRestaurant();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant))
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isForbidden());
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getAll(), List.of(meatHome, bearGrizzly));
    }

    @Test
    void deleteBy() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .content(String.valueOf(MEAT_HOME_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantRepository.getOne(MEAT_HOME_ID));
    }

    @Test
    void deleteIsForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .content(String.valueOf(MEAT_HOME_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isForbidden());
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getAll(), List.of(meatHome, bearGrizzly));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdatedRestaurant();
        perform(MockMvcRequestBuilders.put(REST_URL + BEAR_GRIZZLY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(admin)))
                .andDo(print());
        RESTAURANT_MATCHER.assertMatch(updated, restaurantRepository.getOne(BEAR_GRIZZLY_ID));
    }

    @Test
    void updateUnAuth() throws Exception {
        Restaurant updated = getUpdatedRestaurant();
        perform(MockMvcRequestBuilders.put(REST_URL + BEAR_GRIZZLY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getAll(), List.of(meatHome, bearGrizzly));
    }

    @Test
    void getVotesForRestaurantWithFilter() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + "/votes/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start", "2021-04-20")
                .param("end", "2021-04-26")
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(List.of(VOTE6, VOTE7, VOTE11, VOTE8, VOTE9)));
    }

    @Test
    void getVotesForRestaurantForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + "/votes/today")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(List.of(VOTE15_TODAY)));
    }

    @Test
    void getRestaurantWithVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + "/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_MATCHER_WITHOUT_MENU.contentJson(meatHome));
    }
}