package ru.topjava.graduation.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.topjava.graduation.model.AuthorizedUser;
import ru.topjava.graduation.model.entities.Role;
import ru.topjava.graduation.model.entities.User;

import java.time.LocalDateTime;

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
        return get().getUser().getId();
    }

    public static boolean adminRole(User user) {
        requireNonNull(user, "user must be not null");
        return user.getRoles().contains(Role.ADMIN);
    }
}