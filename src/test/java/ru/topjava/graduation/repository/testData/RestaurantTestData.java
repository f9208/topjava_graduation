package ru.topjava.graduation.repository.testData;

import ru.topjava.graduation.model.entities.Restaurant;
import ru.topjava.graduation.repository.TestMatcher;

import java.util.List;

import static ru.topjava.graduation.model.entities.AbstractNamedEntity.START_SEQ;
import static ru.topjava.graduation.repository.testData.DishTestData.*;
import static ru.topjava.graduation.repository.testData.VoteTestData.*;

public class RestaurantTestData {

    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoreFieldsComparator(Restaurant.class, "menu", "vote");
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER_WITHOUT_VOTE = TestMatcher.usingIgnoreFieldsComparator(Restaurant.class, "vote", "menu.restaurant");
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER_WITHOUT_MENU = TestMatcher.usingIgnoreFieldsComparator(Restaurant.class, "menu", "vote.restaurant");

    public static final int BEAR_GRIZZLY_ID = START_SEQ;
    public static final int MEAT_HOME_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 10;

    public static final Restaurant bearGrizzly = new Restaurant(BEAR_GRIZZLY_ID, "Bear Grizzly");
    public static final Restaurant meatHome = new Restaurant(MEAT_HOME_ID, "Meat Home");

    static {
        bearGrizzly.setMenu(List.of(BORSCHT, CUTLET, COMPOTE, PASTA, PANCAKE));
        meatHome.setMenu(List.of(SOUP, FISH, POTATO, MUFFIN));
        bearGrizzly.setVote(List.of(VOTE2, VOTE3, VOTE4, VOTE10, VOTE12, VOTE14_TODAY));
        meatHome.setVote(List.of(VOTE5, VOTE6, VOTE7, VOTE8, VOTE9, VOTE11, VOTE13, VOTE15_TODAY));
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
