package ru.f9208.choicerestaurant.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.f9208.choicerestaurant.model.entities.Restaurant;
import ru.f9208.choicerestaurant.utils.ValidatorUtil;

import java.util.List;

@Repository
public class RestaurantRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    CrudRestaurantRepository crudRestaurantRepository;

    public RestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @CacheEvict(value = {"allRestaurants","oneRestaurant"}, allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        log.info("create new restaurant");
        return save(restaurant);
    }

    @CacheEvict(value = {"allRestaurants","oneRestaurant"}, allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        log.info("update restaurant with id {}", restaurant.getId());
        save(restaurant);
    }

    @CacheEvict(value = {"allRestaurants","oneRestaurant"}, allEntries = true)
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    @CacheEvict(value = {"allRestaurants","oneRestaurant"}, allEntries = true)
    public boolean delete(int id) {
        log.info("delete restaurant {}", id);
        boolean result = crudRestaurantRepository.delete(id) != 0;
        ValidatorUtil.checkNotFoundWithId(result, id);
        return result;
    }

    @Cacheable(value = "oneRestaurant", key="#id")
    public Restaurant getOne(int id) {
        log.info("get restaurant {}", id);
        return ValidatorUtil.checkNotFoundWithId(crudRestaurantRepository.findById(id).orElse(null), id);
    }

    @Cacheable("allRestaurants")
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return crudRestaurantRepository.findAll();
    }

    public List<Restaurant> getAllWithVotes() {
        log.info("get all restaurants with votes");
        return crudRestaurantRepository.getAllWithVote();
    }
}
