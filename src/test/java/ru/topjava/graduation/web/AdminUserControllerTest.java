package ru.topjava.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduation.model.entities.Role;
import ru.topjava.graduation.model.entities.User;
import ru.topjava.graduation.repository.UserRepository;
import ru.topjava.graduation.web.Exceptions.NotFoundException;
import ru.topjava.graduation.web.json.JsonUtil;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduation.TestUtil.userHttpBasic;
import static ru.topjava.graduation.model.entities.to.VoteTo.convert;
import static ru.topjava.graduation.repository.testData.UserTestData.*;
import static ru.topjava.graduation.repository.testData.VoteTestData.VOTE_TO_TEST_MATCHER;
import static ru.topjava.graduation.repository.testData.VoteTestData.allVotesOfJonny;
import static ru.topjava.graduation.web.AdminUserController.ADMIN_USERS;

class AdminUserControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = ADMIN_USERS + '/';
    @Autowired
    UserRepository userRepository;

    @Test
    void getAdminByAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(admin));
    }

    @Test
    void getUserByAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_JONNY_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(userJonny));
    }

    @Test
    void getUserByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_JONNY_ID)
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(admin, userJonny, userKet, userLeo));
    }

    @Test
    void getVotesForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_JONNY_ID + "/votes")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_TEST_MATCHER.contentJson(convert(allVotesOfJonny)));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(userJonny)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void deleteUser() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL )
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(USER_JONNY_ID))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userRepository.findById(USER_JONNY_ID));
    }

    @Test
    void updateUser() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userRepository.findById(USER_JONNY_ID), getUpdated());
    }

    @Test
    void updateUserIsForbidden() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(userJonny)))
                .andExpect(status().isForbidden());
        USER_MATCHER.assertMatch(userRepository.findById(USER_JONNY_ID), userJonny);
    }

    @Test
    void changeRoles() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(USER_JONNY_ID))
                .param("role", String.valueOf(Role.ADMIN))
                .param("role", String.valueOf(Role.USER))
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        assertEquals(userRepository.findById(USER_JONNY_ID).getRoles(), Set.of(Role.USER, Role.ADMIN));
    }

    @Test
    void changeRoleIsForbidden() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(USER_JONNY_ID))
                .param("role", String.valueOf(Role.ADMIN))
                .param("role", String.valueOf(Role.USER))
                .with(userHttpBasic(userJonny)))
                .andExpect(status().isForbidden())
                .andDo(print());
        assertEquals(userRepository.findById(USER_JONNY_ID).getRoles(), Set.of(Role.USER));
    }
}