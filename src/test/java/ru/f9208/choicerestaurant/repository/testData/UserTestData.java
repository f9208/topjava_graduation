package ru.f9208.choicerestaurant.repository.testData;

import ru.f9208.choicerestaurant.model.entities.Role;
import ru.f9208.choicerestaurant.model.entities.User;
import ru.f9208.choicerestaurant.repository.TestMatcher;

import java.time.LocalDateTime;
import java.util.Set;

import static ru.f9208.choicerestaurant.model.entities.AbstractNamedEntity.START_SEQ;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoreFieldsComparator(User.class, "registered", "password");

    public static final int ADMIN_ID = START_SEQ;
    public static final int USER_JONNY_ID = START_SEQ + 1;
    public static final int USER_KET_ID = START_SEQ + 2;
    public static final int USER_LEO_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "123456789", LocalDateTime.of(21, 04, 3, 12, 10, 33), Role.USER, Role.ADMIN);
    public static final User userJonny = new User(USER_JONNY_ID, "Jonny", "jonny@gmail.com", "passwordJonny", LocalDateTime.of(21, 04, 2, 10, 15, 33), Role.USER);
    public static final User userKet = new User(USER_KET_ID, "Ket", "kety@gmail.com", "passwordKety", LocalDateTime.of(17, 04, 1, 22, 15, 33), Role.USER);
    public static final User userLeo = new User(USER_LEO_ID, "leo", "leonard@gmail.com", "passwordLeon", LocalDateTime.of(20, 06, 1, 20, 13, 43), Role.USER);

    public static User getNew() {
        return new User(null, "novichok", "novichok@kgb.ru", "1234567", LocalDateTime.now(), Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(userJonny);
        updated.setEmail("updateUser@gmail.com");
        updated.setName("viktor");
        updated.setPassword("4834309572");
        updated.setRoles(Set.of(Role.ADMIN, Role.USER));
        return updated;
    }

}
