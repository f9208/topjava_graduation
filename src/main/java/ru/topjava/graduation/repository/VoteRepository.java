package ru.topjava.graduation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduation.model.entities.Restaurant;
import ru.topjava.graduation.model.entities.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.topjava.graduation.utils.ValidatorUtil.*;


@Repository
public class VoteRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CrudVoteRepository crudVoteRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteRepository(CrudVoteRepository crudVoteRepository, RestaurantRepository restaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Vote toVote(LocalDate date, int userId, int restaurantId) {
        log.info("vote for restaurant {} by user {} for {}", restaurantId, userId, date.format(DateTimeFormatter.ISO_DATE));
        if (date == null) date = LocalDateTime.now().toLocalDate();
        Restaurant currentRestaurant = restaurantRepository.getOne(restaurantId);
        Vote savedBeforeVote = crudVoteRepository.findByUserIdAndDate(userId, date);
        if (savedBeforeVote == null) {
            return crudVoteRepository.save(new Vote(null, date, userId, currentRestaurant));
        } else {
            savedBeforeVote.setRestaurant(currentRestaurant);
            return savedBeforeVote;
        }
    }

    public List<Vote> findAll() {
        log.info("get all vote");
        return crudVoteRepository.findAll();
    }

    public Vote get(int id) {
        log.info("get vote {}", id);
        return checkNotFoundWithId(crudVoteRepository.findById(id).orElse(null), id);
    }

    public Vote getVoteByIdAndUserId(int voteId, int userId) {
        return checkNotFoundWithId(crudVoteRepository.findByIdAndUserId(voteId, userId), voteId);
    }

    public List<Vote> getAllBetween(LocalDate start, LocalDate end) {
        return checkNotFoundForDate(crudVoteRepository.getAllByDateBetween(start, end), start, end);
    }

    public List<Vote> getAllForUser(int userId) {
        return crudVoteRepository.getAllByUserId(userId);
    }

    public List<Vote> getAllForRestaurantBetween(LocalDate start, LocalDate end, int userId) {
        return crudVoteRepository.getAllByDateBetweenAndRestaurantId(start, end, userId);
    }

    public Vote getVoteForUserOnDate(int userId, LocalDate date) {
        return crudVoteRepository.getVoteByUserIdAndDate(userId, date);
    }

    public Vote getOneForUser(int voteId, int userId) {
        return crudVoteRepository.getFullByVoteIdAndUserId(voteId, userId);
    }

    @Transactional
    public boolean deleteVote(int id) {
        boolean result = crudVoteRepository.delete(id) != 0;
        checkNotFoundWithId(result, id);
        return result;
    }

    @Transactional
    public boolean deleteVoteForUser(int voteId, int userId) {
        boolean result = crudVoteRepository.deleteVoteByIdAndUserId(voteId, userId) != 0;
        checkNotFoundWithId(result, voteId);
        return result;
    }

    public List<Vote> getAllByDateBetweenAndUserId(LocalDate start, LocalDate end, int userId) {
        return crudVoteRepository.getAllByDateBetweenAndUserId(start, end, userId);
    }

    public long countResultVote(LocalDate day, int restaurantId) {
        return crudVoteRepository.countVoteByDateAndRestaurantId(day, restaurantId);
    }
    public Vote getMyVoteForDay(LocalDate day, int userId){
        return crudVoteRepository.getVoteByDateAndUserId(day,userId);
    }
}
