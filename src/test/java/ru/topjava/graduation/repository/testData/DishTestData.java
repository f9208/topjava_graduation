package ru.topjava.graduation.repository.testData;

import ru.topjava.graduation.model.entities.Dish;
import ru.topjava.graduation.repository.TestMatcher;

import java.time.LocalDateTime;
import java.util.List;

import static ru.topjava.graduation.model.entities.AbstractNamedEntity.START_SEQ;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.bearGrizzly;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.meatHome;

public class DishTestData {

    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingIgnoreFieldsComparator(Dish.class, "added", "restaurant");

    public static final int BORSCHT_ID = START_SEQ;
    public static final int CUTLET_ID = START_SEQ + 1;
    public static final int COMPOTE_ID = START_SEQ + 2;
    public static final int PASTA_ID = START_SEQ + 3;
    public static final int PANCAKE_ID = START_SEQ + 4;
    public static final int SOUP_ID = START_SEQ + 5;
    public static final int FISH_ID = START_SEQ + 6;
    public static final int POTATO_ID = START_SEQ + 7;
    public static final int MUFFIN_ID = START_SEQ + 8;

    public static Dish BORSCHT = new Dish(BORSCHT_ID, "borscht", 40, bearGrizzly, LocalDateTime.parse("2020-04-10T10:00:00"), true);
    public static Dish CUTLET = new Dish(CUTLET_ID, "cutlet", 120, bearGrizzly, LocalDateTime.parse("2020-04-10T10:00:00"), true);
    public static Dish COMPOTE = new Dish(COMPOTE_ID, "compote", 20, bearGrizzly, LocalDateTime.parse("2020-04-10T10:00:00"), true);
    public static Dish PASTA = new Dish(PASTA_ID, "pasta", 100, bearGrizzly, LocalDateTime.parse("2020-04-10T10:00:00"), false);
    public static Dish PANCAKE = new Dish(PANCAKE_ID, "pancake", 30, bearGrizzly, LocalDateTime.parse("2020-04-10T10:00:00"), true);

    public static Dish SOUP = new Dish(SOUP_ID, "soup", 120, meatHome, LocalDateTime.parse("2020-04-10T10:00:00"), false);
    public static Dish FISH = new Dish(FISH_ID, "fish", 160, meatHome, LocalDateTime.parse("2020-04-10T10:00:00"), true);
    public static Dish POTATO = new Dish(POTATO_ID, "potato", 100, meatHome, LocalDateTime.parse("2020-04-10T10:00:00"), true);
    public static Dish MUFFIN = new Dish(MUFFIN_ID, "muffin", 55, meatHome, LocalDateTime.parse("2020-04-10T10:00:00"), false);

    public static List<Dish> BEAR_GRIZZLY_FULL_MENU = List.of(BORSCHT, CUTLET, COMPOTE, PASTA, PANCAKE);
    public static List<Dish> MEAT_HOME_FULL_MENU = List.of(SOUP, FISH, POTATO, MUFFIN);

    public static Dish getNew() {
        return new Dish(null, "burger", 150, meatHome, LocalDateTime.of(2020, 04, 04, 12, 33, 44), true);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(PASTA);
        updated.setName("pasta with meat");
        updated.setPrice(130);
        updated.setEnabled(true);
        return updated;
    }
}
