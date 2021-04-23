package ru.topjava.graduation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.topjava.graduation.model.entities.Restaurant;

import java.util.List;

@Repository
public class RestaurantRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    CrudRestaurantRepository crudRestaurantRepository;

    public RestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        log.info("save restaurant");
        return crudRestaurantRepository.save(restaurant);
    }

    public void delete(int id) {
        log.info("delete restaurant {}", id);
        crudRestaurantRepository.deleteById(id);
    }

    public Restaurant getOne(int id) {
        log.info("get restaurant {}", id);
        return crudRestaurantRepository.findById(id).orElse(null);
    }

    public Restaurant getOneWithMenu(Integer id) {
        log.info("get restaurant {} with menu", id);
        return crudRestaurantRepository.getOneWithMenu(id);
    }

    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return crudRestaurantRepository.findAll();
    }

    public List<Restaurant> getAllWithMenu() {
        log.info("getAll restaurants with menu");
        return crudRestaurantRepository.getAllWithMenu();
    }
}
