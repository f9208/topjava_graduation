package ru.topjava.graduation.utils;

import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.graduation.model.entities.User;
import ru.topjava.graduation.repository.UserRepository;

import java.time.LocalDateTime;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static int authRestaurantId() {
        return 10010;
    }

    public static User getAuthUser() {
        return new User(10000, "user", "user@yandex.ru", "password", LocalDateTime.of(2021, 04, 23, 10, 56, 0));
    }

    public static User getAuthAdmin() {
        return new User(10001, "Admin", "admin@ya.ru", "12345", LocalDateTime.now());
    }
}