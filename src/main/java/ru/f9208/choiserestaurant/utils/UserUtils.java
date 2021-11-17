package ru.f9208.choiserestaurant.utils;

import ru.f9208.choiserestaurant.model.entities.User;
import ru.f9208.choiserestaurant.model.entities.to.UserTo;

public class UserUtils {
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
