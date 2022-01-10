package ru.f9208.choicerestaurant.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.f9208.choicerestaurant.model.entities.Dish;
import ru.f9208.choicerestaurant.repository.DishRepository;

import java.util.List;

import static ru.f9208.choicerestaurant.web.PathConstants.RESTAURANTS_ID_DISHES;

@RestController
@RequestMapping(value = RESTAURANTS_ID_DISHES, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {

    @Autowired
    private DishRepository dishRepository;

    @GetMapping
    public List<Dish> getDishes(@PathVariable("restaurant_id") int restaurantId) {
        return dishRepository.getMenu(restaurantId);
    }

    @GetMapping("/{dishId}")
    public Dish getOne(@PathVariable("dishId") int dishId, @PathVariable("restaurant_id") int restaurantId) {
        return dishRepository.get(dishId, restaurantId);
    }
}
