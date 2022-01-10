package ru.f9208.choicerestaurant.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.f9208.choicerestaurant.model.entities.Dish;
import ru.f9208.choicerestaurant.repository.DishRepository;
import ru.f9208.choicerestaurant.utils.ValidatorUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

import static ru.f9208.choicerestaurant.web.PathConstants.ADMIN_RESTAURANT_DISHES;
import static ru.f9208.choicerestaurant.web.PathConstants.REST_RESTAURANTS;

@RestController
@RequestMapping(value = ADMIN_RESTAURANT_DISHES, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishesRestController {

    @Autowired
    DishRepository dishRepository;

    @GetMapping("/{dishId}")
    public Dish getOne(@PathVariable("dishId") int dishId, @PathVariable("restaurantId") int restaurantId) {
        return dishRepository.get(dishId, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> add(@RequestBody @Valid Dish dish,
                             @PathVariable("restaurantId")
                                     int restaurantId) {
        ValidatorUtil.checkNew(dish);
        if (dish.getDay() == null) dish.setDay(LocalDate.now());
        Dish created = dishRepository.create(dish, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_RESTAURANTS + "/" + restaurantId + "/dishes" + "/{dishId}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "restaurantId") int restaurantId, @RequestBody Dish dish) {
        if (dish.getDay() == null) dish.setDay(LocalDate.now());
        dishRepository.update(dish, restaurantId);
    }

    @DeleteMapping(value = "/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "restaurantId") int restaurantId, @PathVariable int dishId) {
        dishRepository.delete(dishId, restaurantId);
    }
}
