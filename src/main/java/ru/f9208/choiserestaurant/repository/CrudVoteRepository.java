package ru.f9208.choiserestaurant.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.f9208.choiserestaurant.model.entities.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    Vote getVoteByUserIdAndDate(Integer userId, LocalDate date);

    List<Vote> getAllByDateBetween(LocalDate start, LocalDate end);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("Select v From Vote v where v.date>=:start and v.date<=:end and v.restaurant.id=:restaurantId")
    List<Vote> getAllByDateBetweenAndRestaurantId(@Param("start") LocalDate start,
                                                  @Param("end") LocalDate end,
                                                  @Param("restaurantId") Integer restaurantId);

    Vote findByUserIdAndDate(Integer userId, LocalDate date);

    Vote findByIdAndUserId(int id, int userId);

    @Modifying
    @Query("DELETE FROM Vote v where v.id=:id")
    int delete(@Param("id") int id);

    @Modifying
    @Query("DELETE FROM Vote v where v.id=:id and v.userId=:userId")
    int deleteVoteByIdAndUserId(@Param("id") int Id, @Param("userId") int userId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("Select v From Vote v where v.date>=:start and v.date<=:end and v.userId=:userId")
    List<Vote> getAllByDateBetweenAndUserId(@Param("start") LocalDate start,
                                            @Param("end") LocalDate end,
                                            @Param("userId") Integer userId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("Select v From Vote v where v.id=:voteId and v.userId=:userId")
    Vote getFullByVoteIdAndUserId(@Param("voteId") int voteId, @Param("userId") int userId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("Select v From Vote v where v.userId=:userId")
    List<Vote> getAllByUserId(@Param("userId") int userId);

    long countVoteByDateAndRestaurantId(LocalDate day, int restaurantId);

    Vote getVoteByDateAndUserId(LocalDate day, int userId);
}