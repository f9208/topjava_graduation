package ru.f9208.choiserestaurant.web.UI;

import org.springframework.stereotype.Controller;

import static ru.f9208.choiserestaurant.web.PathConstants.RESTAURANTS;
import static ru.f9208.choiserestaurant.web.UI.DishUIController.RESTAURANTS_ID_DISHES;

@Controller(value = RESTAURANTS_ID_DISHES)
public class DishUIController {
    public static final String DISHES = "/dishes";
    public static final String RESTAURANTS_ID_DISHES = RESTAURANTS + "/{restaurant_id}" + DISHES;
}
