package ru.topjava.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduation.Exceptions.NotFoundException;
import ru.topjava.graduation.TestUtil;
import ru.topjava.graduation.model.entities.User;
import ru.topjava.graduation.repository.UserRepository;
import ru.topjava.graduation.repository.testData.UserTestData;
import ru.topjava.graduation.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduation.repository.testData.UserTestData.*;
import static ru.topjava.graduation.web.ProfileController.HEAD_URL;

class ProfileControllerTest extends AbstractRestControllerTest {
    @Autowired
    UserRepository userRepository;

    private static final String REST_URL = HEAD_URL;

    @Test
    void getProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(USER_MATCHER.contentJson(userJonny));
    }

    @Test
    void createProfile() throws Exception {
        User newUser = UserTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().isCreated());
        User created = TestUtil.readFromJson(action, User.class);
        int createdId = created.getId();
        newUser.setId(createdId);
        USER_MATCHER.assertMatch(newUser, created);
        USER_MATCHER.assertMatch(userRepository.findById(createdId), newUser);
    }

    @Test
    void deleteProfile() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(String.valueOf(USER_JONNY_ID)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userRepository.findById(USER_JONNY_ID));
    }

    @Test
    void updateUser() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(updated, userRepository.findById(getUpdated().getId()));
    }
}