package ru.f9208.choiserestaurant.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.f9208.choiserestaurant.TestUtil;
import ru.f9208.choiserestaurant.repository.DishRepository;
import ru.f9208.choiserestaurant.repository.testData.RestaurantTestData;
import ru.f9208.choiserestaurant.repository.testData.UserTestData;
import ru.f9208.choiserestaurant.web.rest.AbstractRestControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.f9208.choiserestaurant.repository.testData.DishTestData.*;
import static ru.f9208.choiserestaurant.web.PathConstants.RESTAURANTS;

public class DishRestControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = RESTAURANTS + "/";
    private static final String DISHES = "/dishes/";

    @Autowired
    DishRepository dishRepository;

    @Test
    void getDishesUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.MEAT_HOME_ID + DISHES))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.MEAT_HOME_ID + DISHES)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(MEAT_HOME_DISHES));
    }

    @Test
    void getOneDish() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.MEAT_HOME_ID + DISHES + POTATO_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(POTATO));
    }


}