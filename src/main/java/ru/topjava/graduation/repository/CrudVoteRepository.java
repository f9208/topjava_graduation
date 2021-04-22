package ru.topjava.graduation.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.topjava.graduation.model.entities.Vote;

import java.util.List;

public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @EntityGraph(attributePaths = {"restaurant","user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("Select v From Vote v where v.id=?1")
     Vote getVoteWithRestaurantAndUser(int id);
}

