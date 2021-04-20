package ru.topjava.graduation.repository;

import org.springframework.stereotype.Repository;

@Repository
public class RestaurantRepository {
    CrudRestaurantRepository crudRestaurantRepository;

    public RestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }


}
