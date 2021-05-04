package ru.topjava.graduation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.topjava.graduation.model.entities.Restaurant;

import java.util.List;

import static ru.topjava.graduation.utils.ValidatorUtil.*;

@Repository
public class RestaurantRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    CrudRestaurantRepository crudRestaurantRepository;

    public RestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        log.info("create new restaurant");
        return save(restaurant);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        log.info("update restaurant with id {}", restaurant.getId());
        save(restaurant);
    }

    @Transactional
    Restaurant save(Restaurant restaurant) {
        log.info("save restaurant");
        return crudRestaurantRepository.save(restaurant);
    }

    public boolean delete(int id) {
        log.info("delete restaurant {}", id);
        boolean result = crudRestaurantRepository.delete(id) != 0;
        checkNotFoundWithId(result, id);
        return result;
    }

    public Restaurant getOne(int id) {
        log.info("get restaurant {}", id);
        return checkNotFoundWithId(crudRestaurantRepository.findById(id).orElse(null), id);
    }

//    public Restaurant getOneWithMenu(Integer id) {
//        log.info("get restaurant {} with menu", id);
//        return crudRestaurantRepository.getOneWithMenu(id);
//    }

    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return crudRestaurantRepository.findAll();
    }

    public List<Restaurant> getAllWithMenu() {
        log.info("getAll restaurants with menu");
        return crudRestaurantRepository.getAllWithMenu();
    }
}
