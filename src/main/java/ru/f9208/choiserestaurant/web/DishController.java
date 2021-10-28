package ru.f9208.choiserestaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.f9208.choiserestaurant.model.entities.Dish;
import ru.f9208.choiserestaurant.repository.DishRepository;

import java.util.List;
import static ru.f9208.choiserestaurant.web.PathConstants.RESTAURANTS_ID_DISHES;

@RestController
@RequestMapping(value = RESTAURANTS_ID_DISHES, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

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
