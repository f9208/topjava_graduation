package ru.f9208.choicerestaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.f9208.choicerestaurant.model.entities.Dish;
import ru.f9208.choicerestaurant.repository.DishRepository;

import java.util.List;

import static ru.f9208.choicerestaurant.web.RestaurantsController.RESTAURANTS;

@RestController
@RequestMapping(value = DishController.RESTAURANT_DISHES, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    public static final String DISHES = "/dishes";
    public static final String RESTAURANT_DISHES = RESTAURANTS + "/{restaurant_id}" + DISHES;

    @Autowired
    private DishRepository dishRepository;

    @GetMapping
    List<Dish> getDishes(@PathVariable("restaurant_id") int restaurantId) {
        return dishRepository.getMenu(restaurantId);
    }

    @GetMapping("/{dish_id}")
    Dish getOne(@PathVariable("dish_id") int dish_id, @PathVariable("restaurant_id") int restaurantId) {
        return dishRepository.get(dish_id, restaurantId);
    }
}
