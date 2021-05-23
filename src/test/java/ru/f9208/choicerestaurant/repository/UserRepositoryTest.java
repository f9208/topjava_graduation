package ru.f9208.choicerestaurant.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.f9208.choicerestaurant.web.exceptions.NotFoundException;
import ru.f9208.choicerestaurant.model.entities.Role;
import ru.f9208.choicerestaurant.model.entities.User;
import ru.f9208.choicerestaurant.repository.testData.UserTestData;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends AbstractStarterTest {
    TestMatcher<User> userMatcher = UserTestData.USER_MATCHER;

    @Autowired
    UserRepository userRepository;

    @Test
    void getOne() {
        User user = userRepository.findById(UserTestData.ADMIN_ID);
        userMatcher.assertMatch(user, UserTestData.admin);
    }

    @Test
    void getAll() {
        List<User> users = userRepository.getAll();
        userMatcher.assertMatch(users, UserTestData.admin, UserTestData.userJonny, UserTestData.userKet, UserTestData.userLeo);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> userRepository.findById(UserTestData.NOT_FOUND));
    }

    @Test
    void create() {
        User created = userRepository.create(UserTestData.getNew());
        int createdId = created.getId();
        User newUser = UserTestData.getNew();
        newUser.setId(createdId);
        UserTestData.USER_MATCHER.assertMatch(created, newUser);
        UserTestData.USER_MATCHER.assertMatch(userRepository.findById(createdId), newUser);
    }

    @Test
    void createDuplicateEmail() {
        assertThrows(DataAccessException.class, () -> userRepository.create(
                new User(null, "Filly", "admin@gmail.com", "12345", LocalDateTime.now(), Role.USER)));
    }

    @Test
    void update() {
        User updated = UserTestData.getUpdated();
        userRepository.update(updated);
        UserTestData.USER_MATCHER.assertMatch(userRepository.findById(UserTestData.USER_JONNY_ID), UserTestData.getUpdated());
    }

    @Test
    void delete() {
        userRepository.delete(UserTestData.USER_KET_ID);
        assertThrows(NotFoundException.class, () -> userRepository.findById(UserTestData.USER_KET_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> userRepository.delete(UserTestData.NOT_FOUND));
    }

    @Test
    void getByEmail() {
        UserTestData.USER_MATCHER.assertMatch(UserTestData.admin, userRepository.getByEmail("admin@gmail.com"));
    }
}