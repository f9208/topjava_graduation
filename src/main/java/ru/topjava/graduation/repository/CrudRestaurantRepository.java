package ru.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.topjava.graduation.model.entities.Restaurant;

public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Override
    Restaurant save(Restaurant restaurant);

    boolean deleteRestaurantById(Integer id);
}
