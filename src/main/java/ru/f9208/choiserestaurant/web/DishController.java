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
import java.util.List;

@RestController
@RequestMapping(value = DishController.RESTAURANT_ID_MENU, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    static final String RESTAURANT_ID_MENU = RestaurantsController.RESTAURANTS + "/{restaurant_id}/menu";
    @Autowired
    DishRepository dishRepository;

    @GetMapping
    List<Dish> getMenu(@PathVariable("restaurant_id") int restaurantId) {
        return dishRepository.getActualMenu(restaurantId);
    }

    @GetMapping("/full")
    List<Dish> getFullMenu(@PathVariable("restaurant_id") int restaurantId) {
        return dishRepository.getFullMenu(restaurantId);
    }

    @GetMapping("/{dish_id}")
    Dish getOneDish(@PathVariable("dish_id") int dish_id, @PathVariable("restaurant_id") int restaurantId) {
        return dishRepository.get(dish_id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Dish> addDish(@RequestBody Dish dish, @PathVariable("restaurant_id") int restaurantId) {
        Dish created = dishRepository.create(dish, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantsController.RESTAURANTS + "/" + restaurantId + "/menu" + "/{dish_id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateDish(@PathVariable(name = "restaurant_id") int restaurant_id, @RequestBody Dish dish) {
        dishRepository.update(dish, restaurant_id);
    }

    @DeleteMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteDish(@PathVariable(name = "restaurant_id") int restaurant_id, @RequestBody int dish_id) {
        dishRepository.delete(dish_id, restaurant_id);
    }

    @PatchMapping(value = "/{dish_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enabled(@PathVariable(name = "restaurant_id") int restaurant_id,
                 @PathVariable(name = "dish_id") int dish_id,
                 @RequestParam boolean enabled) {
        dishRepository.setVisibility(dish_id, restaurant_id, enabled);
    }
}
