package ru.topjava.graduation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.topjava.graduation.model.entities.Dish;
import ru.topjava.graduation.repository.DishRepository;

import javax.persistence.criteria.Root;

@RestController
@RequestMapping(value = RootController.ROOT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RootController {
    static final String ROOT_URL = "/";
    @Autowired
    DishRepository dishRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("{id}")
    public Dish getMain(@PathVariable int id) {
        log.info("get dish {}", id);
        System.out.println("ну гет и гет");
        return dishRepository.get(id);
    }
    @GetMapping
    public void get() {
        System.out.println("просто гет");
    }

}
