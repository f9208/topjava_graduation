package ru.f9208.choiserestaurant.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.model.entities.Vote;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.repository.VoteRepository;
import ru.f9208.choiserestaurant.web.PathConstants;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = PathConstants.REST_RESTAURANTS, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantsRestController {
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