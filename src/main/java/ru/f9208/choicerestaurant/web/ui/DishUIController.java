package ru.f9208.choicerestaurant.web.ui;

import org.springframework.stereotype.Controller;

import static ru.f9208.choicerestaurant.web.PathConstants.REST_RESTAURANTS;
import static ru.f9208.choicerestaurant.web.ui.DishUIController.RESTAURANTS_ID_DISHES;

@Controller(value = RESTAURANTS_ID_DISHES)
public class DishUIController {
    public static final String DISHES = "/dishes";
    public static final String RESTAURANTS_ID_DISHES = REST_RESTAURANTS + "/{restaurant_id}" + DISHES;

    private DishUIController() {
    }
}
