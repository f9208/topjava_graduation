package ru.f9208.choicerestaurant.utils;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.f9208.choicerestaurant.model.HasId;
import ru.f9208.choicerestaurant.model.entities.Vote;
import ru.f9208.choicerestaurant.web.exceptions.IllegalRequestDataException;
import ru.f9208.choicerestaurant.web.exceptions.NotFoundException;

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
        checkNotFound(!object.isEmpty(), "bound value for such dates (since " + start + " to " + end + ")");
        return object;
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureVoteOwner(Vote vote, int userId) {
        if (vote.getUserId() != userId) {
            throw new NotFoundException("vote " + vote + " not belong user with id=" + userId);
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.getId() != id) throw new IllegalRequestDataException(bean + " must be with id=" + id);
    }
}
