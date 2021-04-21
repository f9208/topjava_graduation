package ru.topjava.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.graduation.model.entities.Dish;
import ru.topjava.graduation.repository.DishRepository;
import ru.topjava.graduation.repository.RestaurantRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = DishController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    static final String PATH = "/restaurants/{restaurant_id}/dishes";

    @Autowired
    DishRepository dishRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping
    List<Dish> get(@PathVariable(name = "restaurant_id") int id) {
        return dishRepository.getAllByRestaurantId(id);
    }

    @GetMapping("/{id}")
    Dish getOne(@PathVariable("id") int id, @PathVariable("restaurant_id") int restaurantId) {
        return dishRepository.get(id, restaurantId);
    }

    //todo как здесь сделать аутентификацию?
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Dish> addDish(@RequestBody Dish dish, @PathVariable("restaurant_id") int restaurantId) {
        Dish created = dishRepository.save(dish, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(PATH + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    //todo как здесь сделать аутентификацию?
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable("restaurant_id") int restaurantId) {
        dishRepository.save(dish, restaurantId);
    }


}
