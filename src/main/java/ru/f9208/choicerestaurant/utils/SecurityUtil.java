package ru.f9208.choicerestaurant.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.f9208.choicerestaurant.model.AuthorizedUser;
import ru.f9208.choicerestaurant.model.entities.Role;
import ru.f9208.choicerestaurant.model.entities.User;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        return requireNonNull(safeGet(), "No authorized user found");
    }

    public static int getAuthUserId() {
        return get().getUserTo().getId();
    }

    public static boolean chekNotAdmin(User user) {
        requireNonNull(user, "user must be not null");
        return user.getRoles().contains(Role.ADMIN);
    }
}