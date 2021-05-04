package ru.topjava.graduation.utils;

import ru.topjava.graduation.Exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public class ValidatorUtil {

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> List<T> checkNotFoundForDate(List<T> object, LocalDate start, LocalDate end) {
        checkNotFound(!object.isEmpty(), "bound value for such dates (since " + start + " to " + end+")");
        return object;
    }
}
