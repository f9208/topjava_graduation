package ru.topjava.graduation.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.topjava.graduation.model.entities.Restaurant;

public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
    void deleteById(Integer id);

    // https://stackoverflow.com/a/46013654/548473 //todo fucking magic
    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id=?1")
    Restaurant getOneWithDish(Integer id);
}
