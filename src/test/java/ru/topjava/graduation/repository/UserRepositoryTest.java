package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.topjava.graduation.Exceptions.NotFoundException;
import ru.topjava.graduation.model.entities.Role;
import ru.topjava.graduation.model.entities.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.topjava.graduation.repository.testData.UserTestData.*;

class UserRepositoryTest extends AbstractStarterTest {
    TestMatcher<User> userMatcher = USER_MATCHER;

    @Autowired
    UserRepository userRepository;

    @Test
    void getOne() {
        User user = userRepository.findById(ADMIN_ID);
        userMatcher.assertMatch(user, admin);
    }

    @Test
    void getAll() {
        List<User> users = userRepository.getAll();
        userMatcher.assertMatch(users, admin, userJonny, userKet, userLeo);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> userRepository.findById(NOT_FOUND));
    }

    @Test
    void create() {
        User created = userRepository.create(getNew());
        int createdId = created.getId();
        User newUser = getNew();
        newUser.setId(createdId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userRepository.findById(createdId), newUser);
    }

    @Test
    void createDuplicateEmail() {
        assertThrows(DataAccessException.class, () -> userRepository.create(
                new User(null, "Filly", "admin@gmail.com", "12345", LocalDateTime.now(), Role.USER)));
    }

    @Test
    void update() {
        User updated = getUpdated();
        userRepository.update(updated);
        USER_MATCHER.assertMatch(userRepository.findById(USER_JONNY_ID), getUpdated());
    }

    @Test
    void delete() {
        userRepository.delete(USER_KET_ID);
        assertThrows(NotFoundException.class, () -> userRepository.findById(USER_KET_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> userRepository.delete(NOT_FOUND));
    }

    @Test
    void getByEmail() {
        USER_MATCHER.assertMatch(admin, userRepository.getByEmail("admin@gmail.com"));
    }
}