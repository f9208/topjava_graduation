package ru.f9208.choiserestaurant.repository.testData;

import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.repository.TestMatcher;

import java.util.List;

import static ru.f9208.choiserestaurant.model.entities.AbstractNamedEntity.START_SEQ;
import static ru.f9208.choiserestaurant.repository.testData.DishTestData.*;

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
        bearGrizzly.setVote(List.of(VoteTestData.VOTE2, VoteTestData.VOTE3, VoteTestData.VOTE4, VoteTestData.VOTE10, VoteTestData.VOTE12, VoteTestData.VOTE14_TODAY_RE_VOTE));
        meatHome.setVote(List.of(VoteTestData.VOTE5, VoteTestData.VOTE6, VoteTestData.VOTE7, VoteTestData.VOTE8, VoteTestData.VOTE9, VoteTestData.VOTE11, VoteTestData.VOTE13, VoteTestData.VOTE15_TODAY));
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
