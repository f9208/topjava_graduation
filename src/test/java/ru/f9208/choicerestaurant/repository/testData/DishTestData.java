package ru.f9208.choicerestaurant.repository.testData;

import ru.f9208.choicerestaurant.model.entities.Dish;
import ru.f9208.choicerestaurant.repository.TestMatcher;

import java.util.List;

import static ru.f9208.choicerestaurant.model.entities.AbstractNamedEntity.START_SEQ;
import static ru.f9208.choicerestaurant.repository.testData.VoteTestData.TODAY;

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

    public static Dish BORSCHT = new Dish(BORSCHT_ID, "borscht", 40, RestaurantTestData.bearGrizzly, TODAY);
    public static Dish CUTLET = new Dish(CUTLET_ID, "cutlet", 120, RestaurantTestData.bearGrizzly, TODAY);
    public static Dish COMPOTE = new Dish(COMPOTE_ID, "compote", 20, RestaurantTestData.bearGrizzly, TODAY);
    public static Dish PASTA = new Dish(PASTA_ID, "pasta", 100, RestaurantTestData.bearGrizzly, TODAY);
    public static Dish PANCAKE = new Dish(PANCAKE_ID, "pancake", 30, RestaurantTestData.bearGrizzly, TODAY);

    public static Dish SOUP = new Dish(SOUP_ID, "soup", 120, RestaurantTestData.meatHome,TODAY);
    public static Dish FISH = new Dish(FISH_ID, "fish", 160, RestaurantTestData.meatHome, TODAY);
    public static Dish POTATO = new Dish(POTATO_ID, "potato", 100, RestaurantTestData.meatHome, TODAY);
    public static Dish MUFFIN = new Dish(MUFFIN_ID, "muffin", 55, RestaurantTestData.meatHome, TODAY);

    public static List<Dish> MEAT_HOME_DISHES = List.of(SOUP, FISH, POTATO, MUFFIN);

    public static Dish getNew() {
        return new Dish(null, "burger", 150, RestaurantTestData.meatHome, TODAY);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(PASTA);
        updated.setName("pasta with meat");
        updated.setPrice(130);
        return updated;
    }
}
