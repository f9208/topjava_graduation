package ru.f9208.choiserestaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.f9208.choiserestaurant.TestUtil;
import ru.f9208.choiserestaurant.model.entities.Role;
import ru.f9208.choiserestaurant.repository.UserRepository;
import ru.f9208.choiserestaurant.repository.testData.UserTestData;
import ru.f9208.choiserestaurant.web.exceptions.NotFoundException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.f9208.choiserestaurant.web.AdminUserController.ADMIN_USERS;

class AdminUserControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = ADMIN_USERS + '/';
    @Autowired
    UserRepository userRepository;

    @Test
    void getAdminByAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + UserTestData.ADMIN_ID)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.admin));
    }

    @Test
    void getUserByAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + UserTestData.USER_JONNY_ID)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.userJonny));
    }

    @Test
    void getUserByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + UserTestData.USER_JONNY_ID)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.admin, UserTestData.userJonny, UserTestData.userKet, UserTestData.userLeo));
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
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void deleteUser() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + UserTestData.USER_JONNY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(UserTestData.USER_JONNY_ID))
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userRepository.findById(UserTestData.USER_JONNY_ID));
    }

    @Test
    void changeRoles() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(UserTestData.USER_JONNY_ID))
                .param("role", String.valueOf(Role.ADMIN))
                .param("role", String.valueOf(Role.USER))
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isNoContent());
        assertEquals(userRepository.findById(UserTestData.USER_JONNY_ID).getRoles(), Set.of(Role.USER, Role.ADMIN));
    }

    @Test
    void changeRoleIsForbidden() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(UserTestData.USER_JONNY_ID))
                .param("role", String.valueOf(Role.ADMIN))
                .param("role", String.valueOf(Role.USER))
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andExpect(status().isForbidden())
                .andDo(print());
        assertEquals(userRepository.findById(UserTestData.USER_JONNY_ID).getRoles(), Set.of(Role.USER));
    }
}