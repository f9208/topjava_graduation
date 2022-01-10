package ru.f9208.choicerestaurant.utils;

import ru.f9208.choicerestaurant.model.entities.User;
import ru.f9208.choicerestaurant.model.entities.to.UserTo;

public class UserUtils {
    private UserUtils() {
    }

    public static User updateFromTo(User user, UserTo userTo) {

        user.setEmail(userTo.getEmail().toLowerCase());
        user.setName(userTo.getName());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
}
