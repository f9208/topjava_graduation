package ru.topjava.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.graduation.model.entities.Restaurant;
import ru.topjava.graduation.repository.DishRepository;
import ru.topjava.graduation.repository.RestaurantRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantsController.RESTAURANT_ROOT, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantsController {
    static final String RESTAURANT_ROOT = "/";
    static final String RESTAURANTS = "restaurants";
    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping
    public String getRootPage() {
        return "hello! this is main page";
    }

    @GetMapping(RESTAURANTS)
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @GetMapping(RESTAURANTS + "/{id}")
    public Restaurant getRestaurantWithMenu(@PathVariable("id") int id) {
        return restaurantRepository.getOne(id);
    }

    @GetMapping(RESTAURANTS + "/with-menu")
    public List<Restaurant> getAllWithMenu() {
        return restaurantRepository.getAllWithMenu();
    }

    @PostMapping(value = RESTAURANTS, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantRepository.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANTS + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(RESTAURANTS + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        restaurantRepository.delete(id);
    }

    @DeleteMapping(RESTAURANTS)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBy(@RequestParam(name = "id") int id) {
        restaurantRepository.delete(id);
    }

    @PutMapping(value = RESTAURANTS + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "id") int id, @RequestBody Restaurant restaurant) {
        restaurant.setId(id);
        restaurantRepository.update(restaurant);
    }
}
