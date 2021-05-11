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
@RequestMapping(value = RestaurantsController.RESTAURANTS, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantsController {
    static final String RESTAURANTS = "/restaurants";
    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @GetMapping("/with-menu")
    public List<Restaurant> getAllWithMenu() {
        return restaurantRepository.getAllWithMenu();
    }

    @GetMapping("/{id}")
    public Restaurant getOneRestaurant(@PathVariable("id") int id) {
        return restaurantRepository.getOne(id);
    }

    @GetMapping("/with-menu/{id}")
    public Restaurant getOneRestaurantWithMenu(@PathVariable("id") int id) {
        return restaurantRepository.getOneWithMenu(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantRepository.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANTS + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBy(@RequestParam(name = "id") int id) {
        restaurantRepository.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "id") int id, @RequestBody Restaurant restaurant) {
        restaurant.setId(id);
        restaurantRepository.update(restaurant);
    }
}
