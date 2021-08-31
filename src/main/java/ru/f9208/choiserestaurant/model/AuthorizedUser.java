package ru.f9208.choiserestaurant.model;

import ru.f9208.choiserestaurant.model.entities.User;
import ru.f9208.choiserestaurant.model.entities.to.UserTo;
import ru.f9208.choiserestaurant.utils.UserUtils;

import java.io.Serial;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;
    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        this.userTo = UserUtils.asTo(user);
        System.out.println("айдишник userTo из AuthorizedUser: "+userTo.getId());
    }

    public UserTo getUserTo() {
        return userTo;
    }

    public void setUserTo(UserTo userTo) {
        this.userTo = userTo;
    }
}
