package ru.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduation.model.entities.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
//
//    @Query("Select v From Vote v where v.id=?1")
//     Vote getVoteWithRestaurantAndUser(int id);
//
//    @EntityGraph(attributePaths = {"restaurant","user"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query("Select v From Vote v")
//    List <Vote> getAllWithRestaurantAndUser();

    Vote getVoteByUserIdAndDate(Integer userId, LocalDate date);

    List<Vote> getAllByDateBetween(LocalDate start, LocalDate end);

    Optional<List<Vote>> getAllByUserId(Integer userId);

    List<Vote> getAllByDateBetweenAndUserId(LocalDate start, LocalDate end, Integer userId);

    Vote findByUserIdAndDate(Integer userId, LocalDate date);

    Vote findByIdAndUserId(int id, int userId);

    @Modifying
    @Query("DELETE FROM Vote v where v.id=:id")
    int delete(@Param("id") int id);

    @Modifying
    @Query("DELETE FROM Vote v where v.id=:id and v.userId=:userId")
    int deleteVoteByIdAndUserId(@Param("id") int Id, @Param("userId") int userId);
}

