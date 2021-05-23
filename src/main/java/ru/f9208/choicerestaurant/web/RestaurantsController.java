package ru.f9208.choicerestaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.f9208.choicerestaurant.model.entities.Restaurant;
import ru.f9208.choicerestaurant.model.entities.Vote;
import ru.f9208.choicerestaurant.repository.RestaurantRepository;
import ru.f9208.choicerestaurant.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantsController.RESTAURANTS, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantsController {
    public static final String RESTAURANTS = "/restaurants";
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    VoteRepository voteRepository;

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable("id") int id) {
        return restaurantRepository.getOne(id);
    }

    @GetMapping("/{id}/votes")
    public List<Vote> getVotes(@RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate start,
                               @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate end,
                               @PathVariable("id") int id) {
        if (start == null) start = LocalDate.now();
        if (end == null) end = LocalDate.now();
        return voteRepository.getAllForRestaurantBetween(start, end, id);
    }
}