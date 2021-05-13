package ru.topjava.graduation.repository.testData;

import ru.topjava.graduation.model.entities.Restaurant;
import ru.topjava.graduation.repository.TestMatcher;

import java.util.List;

import static ru.topjava.graduation.model.entities.AbstractNamedEntity.START_SEQ;
import static ru.topjava.graduation.repository.testData.DishTestData.*;

public class RestaurantTestData {

    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoreFieldsComparator(Restaurant.class, "menu", "vote");

    public static final int BEAR_GRIZZLY_ID = START_SEQ;
    public static final int MEAT_HOME_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 10;

    public static final Restaurant bearGrizzly = new Restaurant(BEAR_GRIZZLY_ID, "Bear Grizzly");
    public static final Restaurant meatHome = new Restaurant(MEAT_HOME_ID, "Meat Home");

    static {
        bearGrizzly.setMenu(List.of(BORSCHT, CUTLET, COMPOTE, PASTA, PANCAKE));
        meatHome.setMenu(List.of(SOUP, FISH, POTATO, MUFFIN));
    }

    public static Restaurant getNewRestaurant() {
        return new Restaurant(null, "BurgerKing");
    }

    public static Restaurant getUpdatedRestaurant() {
        Restaurant updated = new Restaurant(bearGrizzly.getId(), "White Bear");
        updated.setMenu(bearGrizzly.getMenu());
        return updated;
    }
}
