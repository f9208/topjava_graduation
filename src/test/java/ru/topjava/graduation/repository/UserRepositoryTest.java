package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserRepositoryTest extends AbstractStarterTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void getOne() {
        System.out.println(userRepository.getOne(10001));
    }

    @Test
    void getAll() {
        System.out.println(userRepository.getAll());
    }
}