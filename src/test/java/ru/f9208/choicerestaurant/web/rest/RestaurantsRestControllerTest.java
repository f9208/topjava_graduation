package ru.f9208.choicerestaurant.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.f9208.choicerestaurant.TestUtil;
import ru.f9208.choicerestaurant.repository.RestaurantRepository;
import ru.f9208.choicerestaurant.repository.testData.RestaurantTestData;
import ru.f9208.choicerestaurant.repository.testData.UserTestData;
import ru.f9208.choicerestaurant.repository.testData.VoteTestData;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.f9208.choicerestaurant.repository.testData.RestaurantTestData.MEAT_HOME_ID;
import static ru.f9208.choicerestaurant.web.PathConstants.REST_RESTAURANTS;

class RestaurantsRestControllerTest extends AbstractRestControllerTest {
    @Autowired
    RestaurantRepository restaurantRepository;

    private final String REST_URL = REST_RESTAURANTS + "/";

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(List.of(RestaurantTestData.meatHome, RestaurantTestData.bearGrizzly)));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(List.of(RestaurantTestData.meatHome)));
    }

    @Test
    void getVotesForRestaurantWithFilter() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAT_HOME_ID + "/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start", "2021-04-20")
                .param("end", "2021-04-26")
                .param("id", String.valueOf(MEAT_HOME_ID))
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.VOTE_TEST_MATCHER.contentJson(List.of(VoteTestData.VOTE5, VoteTestData.VOTE6, VoteTestData.VOTE10, VoteTestData.VOTE7, VoteTestData.VOTE8)));
    }
}