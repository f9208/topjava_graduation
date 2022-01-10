package ru.f9208.choicerestaurant.model;

import ru.f9208.choicerestaurant.model.entities.User;
import ru.f9208.choicerestaurant.model.entities.to.UserTo;
import ru.f9208.choicerestaurant.utils.UserUtils;

import java.io.Serial;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;
    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        this.userTo = UserUtils.asTo(user);
    }

    public UserTo getUserTo() {
        return userTo;
    }

    public void setUserTo(UserTo userTo) {
        this.userTo = userTo;
    }
}
