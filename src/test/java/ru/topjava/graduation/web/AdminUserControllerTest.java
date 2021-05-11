package ru.topjava.graduation.web;

import org.ehcache.core.util.CollectionUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduation.Exceptions.NotFoundException;
import ru.topjava.graduation.model.entities.Role;
import ru.topjava.graduation.model.entities.User;
import ru.topjava.graduation.repository.UserRepository;
import ru.topjava.graduation.web.json.JsonUtil;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduation.repository.testData.UserTestData.*;

class AdminUserControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = AdminUserController.ADMIN_USERS + '/';
    @Autowired
    UserRepository userRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(admin));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(admin, userJonny, userKet, userLeo));
    }

    @Test
    void deleteUser() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + userJonny.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userRepository.findById(userJonny.getId()));
    }

    @Test
    void updateUser() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userRepository.findById(USER_JONNY_ID), getUpdated());
    }

    @Test
    void changeRoles() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(USER_JONNY_ID))
                .param("role", String.valueOf(Role.ADMIN))
                .param("role", String.valueOf(Role.USER)))
                .andExpect(status().isNoContent());
        assertEquals(userRepository.findById(USER_JONNY_ID).getRoles(), Set.of(Role.USER, Role.ADMIN));
    }
}