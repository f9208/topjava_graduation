package ru.topjava.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.graduation.model.entities.Dish;
import ru.topjava.graduation.repository.DishRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RootController.ROOT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RootController {
    static final String ROOT_URL = "/";
    @Autowired
    DishRepository dishRepository;

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        return dishRepository.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        dishRepository.delete(id);
    }

    @GetMapping
    public List<Dish> getAll() {
        return dishRepository.getAll();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish) {
        dishRepository.update(dish);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@RequestBody Dish dish) {
        Dish created = dishRepository.create(dish);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ROOT_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
