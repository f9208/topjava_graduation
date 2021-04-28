package ru.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.topjava.graduation.model.entities.Vote;

import java.time.LocalDate;
import java.util.List;

public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
//
//    @Query("Select v From Vote v where v.id=?1")
//     Vote getVoteWithRestaurantAndUser(int id);
//
//    @EntityGraph(attributePaths = {"restaurant","user"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query("Select v From Vote v")
//    List <Vote> getAllWithRestaurantAndUser();


    List<Vote> getAllByDateBetween(LocalDate start, LocalDate end);

    List<Vote> getAllByUserId(Integer userId);

    List<Vote> getAllByDateBetweenAndUserId(LocalDate start, LocalDate end, Integer userId);

    Vote findByUserIdAndDate(Integer userId, LocalDate date);

    void deleteVoteByIdAndUserId(int Id, int userId);
}

