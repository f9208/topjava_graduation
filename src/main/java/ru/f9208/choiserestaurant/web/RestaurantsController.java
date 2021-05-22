package ru.f9208.choiserestaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.f9208.choiserestaurant.model.entities.Vote;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.repository.VoteRepository;
import ru.f9208.choiserestaurant.utils.DateTimeUtils;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantsController.RESTAURANTS, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantsController {
    static final String RESTAURANTS = "/restaurants";
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    VoteRepository voteRepository;

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @GetMapping("/with-menu")
    public List<Restaurant> getAllWithMenu() {
        return restaurantRepository.getAllWithMenu();
    }

    @GetMapping("/with-menu/{id}")
    public Restaurant getOneRestaurantWithMenu(@PathVariable("id") int id) {
        return restaurantRepository.getOneWithMenu(id);
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable("id") int id) {
        return restaurantRepository.getOne(id);
    }

    @GetMapping("/{id}/votes/filter")
    public List<Vote> getVotes(@RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate start,
                               @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate end,
                               @PathVariable("id") int id) {
        if (start == null) start = DateTimeUtils.MIN_DATE;
        if (end == null) end = DateTimeUtils.MAX_DATE;
        return voteRepository.getAllForRestaurantBetween(start, end, id);
    }

    @GetMapping("/{id}/votes/today")
    public List<Vote> getVotesToday(@PathVariable("id") int id) {
        return voteRepository.getAllForRestaurantBetween(LocalDate.now(), LocalDate.now(), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        Restaurant created = restaurantRepository.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANTS + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBy(@RequestBody Integer id) {
        restaurantRepository.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "id") int id, @Valid @RequestBody Restaurant restaurant) {
        restaurant.setId(id);
        restaurantRepository.update(restaurant);
    }

    @GetMapping("/{id}/votes")
    public Restaurant getRestaurantWithVotes(@PathVariable("id") int id) {
        return restaurantRepository.getOneWithVotes(id);
    }
}
