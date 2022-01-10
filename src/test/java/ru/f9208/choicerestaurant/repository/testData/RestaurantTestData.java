package ru.f9208.choicerestaurant.repository.testData;

import ru.f9208.choicerestaurant.model.entities.Restaurant;
import ru.f9208.choicerestaurant.repository.TestMatcher;

import java.util.List;

import static ru.f9208.choicerestaurant.model.entities.AbstractNamedEntity.START_SEQ;
import static ru.f9208.choicerestaurant.repository.testData.DishTestData.*;
import static ru.f9208.choicerestaurant.repository.testData.ImageLabelData.*;

public class RestaurantTestData {

    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoreFieldsComparator(Restaurant.class, "menu", "vote");

    public static final int BEAR_GRIZZLY_ID = START_SEQ;
    public static final int MEAT_HOME_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 10;

    public static final Restaurant bearGrizzly = new Restaurant(BEAR_GRIZZLY_ID, "bearGrizzly", bearGrizzlyLabel, "Здесь вы найдете малину, шишки и немного сырой зайчатины!", true);
    public static final Restaurant meatHome = new Restaurant(MEAT_HOME_ID, "meatHome", meatHomeLabel, "много мясо, мяса и еще больше мяса! да с огоньком!", false);
    public static final Restaurant teaHome = new Restaurant(null, "teaHomeCafe", null, "традиционная кухня!", true);

    static {
        bearGrizzly.setMenu(List.of(BORSCHT, CUTLET, COMPOTE, PASTA, PANCAKE));
        meatHome.setMenu(List.of(SOUP, FISH, POTATO, MUFFIN));
        bearGrizzly.setVote(List.of(VoteTestData.VOTE1, VoteTestData.VOTE2, VoteTestData.VOTE3, VoteTestData.VOTE9, VoteTestData.VOTE11, VoteTestData.VOTE13_TODAY_RE_VOTE));
        meatHome.setVote(List.of(VoteTestData.VOTE4, VoteTestData.VOTE5, VoteTestData.VOTE6, VoteTestData.VOTE7, VoteTestData.VOTE8, VoteTestData.VOTE10, VoteTestData.VOTE12, VoteTestData.VOTE14_TODAY));
    }

    public static Restaurant getNewRestaurant() {
        return new Restaurant(null, "BurgerKing", null, null);
    }

    public static Restaurant getUpdatedRestaurant() {
        Restaurant updated = new Restaurant(bearGrizzly.getId(), "White Bear", whiteBear, "какое то новое место");
        return updated;
    }
}
