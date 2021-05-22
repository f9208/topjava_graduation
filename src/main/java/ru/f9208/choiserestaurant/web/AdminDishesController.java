package ru.f9208.choiserestaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.f9208.choiserestaurant.model.entities.Dish;
import ru.f9208.choiserestaurant.repository.DishRepository;

import java.net.URI;

import static ru.f9208.choiserestaurant.web.AdminDishesController.ADMIN_RESTAURANT_DISHES;
import static ru.f9208.choiserestaurant.web.AdminRestaurantController.ADMIN_RESTAURANT;
import static ru.f9208.choiserestaurant.web.RestaurantsController.RESTAURANTS;

@RestController
@RequestMapping(value = ADMIN_RESTAURANT_DISHES, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishesController {
    public static final String ID = "/{restaurant_id}";
    public static final String DISHES = "/dishes";
    public static final String ADMIN_RESTAURANT_DISHES = ADMIN_RESTAURANT + ID + DISHES;

    @Autowired
    DishRepository dishRepository;

    @GetMapping("/{dish_id}")
    Dish getOne(@PathVariable("dish_id") int dish_id, @PathVariable("restaurant_id") int restaurantId) {
        return dishRepository.get(dish_id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Dish> add(@RequestBody Dish dish,
                             @PathVariable("restaurant_id")
                                     int restaurantId) {
        Dish created = dishRepository.create(dish, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANTS + "/" + restaurantId + "/dishes" + "/{dish_id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable(name = "restaurant_id") int restaurant_id, @RequestBody Dish dish) {
        dishRepository.update(dish, restaurant_id);
    }

    @DeleteMapping(value = "/{dish_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable(name = "restaurant_id") int restaurant_id, @PathVariable int dish_id) {
        dishRepository.delete(dish_id, restaurant_id);
    }
}
