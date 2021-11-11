package ru.f9208.choiserestaurant.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.f9208.choiserestaurant.TestUtil;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.repository.testData.RestaurantTestData;
import ru.f9208.choiserestaurant.repository.testData.UserTestData;
import ru.f9208.choiserestaurant.web.exceptions.NotFoundException;
import ru.f9208.choiserestaurant.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.f9208.choiserestaurant.web.PathConstants.ADMIN_RESTAURANTS;

class AdminRestaurantRestControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = ADMIN_RESTAURANTS;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNewRestaurant();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant))
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isCreated());
        Restaurant created = TestUtil.readFromJson(actions, Restaurant.class);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(created, restaurantRepository.getOne(created.getId()));
    }

    @Test
    void createIsForbidden() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNewRestaurant();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant))
                .with(TestUtil.userHttpBasic(UserTestData.userKet)))
                .andDo(print())
                .andExpect(status().isForbidden());
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurantRepository.getAll(), List.of(RestaurantTestData.meatHome, RestaurantTestData.bearGrizzly));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + RestaurantTestData.MEAT_HOME_ID)
                .content(String.valueOf(RestaurantTestData.MEAT_HOME_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantRepository.getOne(RestaurantTestData.MEAT_HOME_ID));
    }

    @Test
    void deleteIsForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL +"/"+ RestaurantTestData.MEAT_HOME_ID)
                .content(String.valueOf(RestaurantTestData.MEAT_HOME_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(UserTestData.userKet)))
                .andDo(print())
                .andExpect(status().isForbidden());
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurantRepository.getAll(), List.of(RestaurantTestData.meatHome, RestaurantTestData.bearGrizzly));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdatedRestaurant();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + RestaurantTestData.BEAR_GRIZZLY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print());
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(updated, restaurantRepository.getOne(RestaurantTestData.BEAR_GRIZZLY_ID));
    }

    @Test
    void updateUnAuth() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdatedRestaurant();
        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.BEAR_GRIZZLY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurantRepository.getAll(), List.of(RestaurantTestData.meatHome, RestaurantTestData.bearGrizzly));
    }

}