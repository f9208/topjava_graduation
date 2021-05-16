package ru.topjava.graduation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @CacheEvict(value = {"allRestaurants", "allRestaurantsWithMenu", "getOneWithMenu"}, allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        log.info("create new restaurant");
        return save(restaurant);
    }

    @CacheEvict(value = {"allRestaurants", "allRestaurantsWithMenu", "getOneWithMenu"}, allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        log.info("update restaurant with id {}", restaurant.getId());
        save(restaurant);
    }

    @CacheEvict(value = {"allRestaurants", "allRestaurantsWithMenu", "getOneWithMenu"}, allEntries = true)
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    @CacheEvict(value = {"allRestaurants", "allRestaurantsWithMenu", "getOneWithMenu"}, allEntries = true)
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
    @Cacheable(value = "getOneWithMenu", key="#id")
    public Restaurant getOneWithMenu(int id) {
        log.info("get restaurant {} with menu", id);
        return checkNotFoundWithId(crudRestaurantRepository.getOneWithMenu(id), id);
    }

    @Cacheable("allRestaurants")
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return crudRestaurantRepository.findAll();
    }

    @Cacheable("allRestaurantsWithMenu")
    public List<Restaurant> getAllWithMenu() {
        log.info("getAll restaurants with menu");
        return crudRestaurantRepository.getAllWithMenu();
    }

    public List<Restaurant> getAllWithVotes() {
        log.info("get all restaurants with votes");
        return crudRestaurantRepository.getAllWithVote();
    }

    public Restaurant getOneWithVotes(int id) {
        log.info("get one restaurant with votes by id = {}", id);
        return crudRestaurantRepository.getOneWithVote(id);
    }
}
