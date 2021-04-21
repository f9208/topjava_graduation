package ru.topjava.graduation.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.topjava.graduation.model.entities.Restaurant;

import java.util.List;
import java.util.Optional;

public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Override
    Restaurant save(Restaurant restaurant);

    boolean deleteRestaurantById(Integer id);

    List<Restaurant> findAll();

//    SELECT * FROM Restaurant r JOIN DISHES D on r.ID = D.RESTAURANT_ID;

//    @Query("SELECT r FROM Restaurant r JOIN FETCH Dish D on D.restaurant_id = :restaurant_id")
//    Restaurant getWithDish(@Param("restaurant_id") Integer id);


//    @EntityGraph(attributePaths = {"meals"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query("SELECT u FROM User u WHERE u.id=?1")
//    User getWithMeals(int id);
}
