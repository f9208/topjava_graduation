package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.graduation.model.entities.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.topjava.graduation.UserTestData.*;

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
//        assertThrows(NotFoundException.class, () -> userRepository.findById(NOT_FOUND));
//        экспешены создаются в валидаторе, т.е. вначале надо создать его.
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
    void delete() {
        userRepository.deleteUser(USER_KET_ID);
        //        экспешены создаются в валидаторе, т.е. вначале надо создать его.

//        assertThrows(NotFoundException.class, () -> userRepository.findById(USER_KET_ID));
    }
}