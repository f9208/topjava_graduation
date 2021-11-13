package ru.f9208.choiserestaurant.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.f9208.choiserestaurant.model.entities.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") Integer id);

    @EntityGraph(attributePaths = {"vote"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r")
    List<Restaurant> getAllWithVote();

    @Query("SELECT DISTINCT new ru.f9208.choiserestaurant.model.entities.Restaurant (r.id, r.name, r.label, r.description)" +
            " FROM Restaurant r LEFT JOIN Vote v ON r.id=v.restaurant.id where v.day>=:start and v.day<=:end")
    List<Restaurant> getAllBetweenDay(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
