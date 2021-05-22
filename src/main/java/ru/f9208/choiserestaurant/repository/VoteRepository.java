package ru.f9208.choiserestaurant.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.model.entities.Vote;
import ru.f9208.choiserestaurant.utils.ValidatorUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


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
        return ValidatorUtil.checkNotFoundWithId(crudVoteRepository.findById(id).orElse(null), id);
    }

    public Vote getVoteByIdAndUserId(int voteId, int userId) {
        return ValidatorUtil.checkNotFoundWithId(crudVoteRepository.findByIdAndUserId(voteId, userId), voteId);
    }

    public List<Vote> getAllBetween(LocalDate start, LocalDate end) {
        return ValidatorUtil.checkNotFoundForDate(crudVoteRepository.getAllByDateBetween(start, end), start, end);
    }

    public List<Vote> getAllForUser(int userId) {
        return ValidatorUtil.checkNotFoundWithId(crudVoteRepository.getAllByUserId(userId), userId);
    }

    public List<Vote> getAllForRestaurantBetween(LocalDate start, LocalDate end, int userId) {
        return ValidatorUtil.checkNotFoundForDate(crudVoteRepository.getAllByDateBetweenAndRestaurantId(start, end, userId), start, end);
    }

    public Vote getVoteForUserOnDate(int userId, LocalDate date) {
        return ValidatorUtil.checkNotFoundWithId(crudVoteRepository.getVoteByUserIdAndDate(userId, date), userId);
    }

    public Vote getOneForUser(int voteId, int userId) {
        return ValidatorUtil.checkNotFoundWithId(crudVoteRepository.getFullByVoteIdAndUserId(voteId, userId), voteId);
    }

    @Transactional
    public boolean deleteVote(int id) {
        boolean result = crudVoteRepository.delete(id) != 0;
        ValidatorUtil.checkNotFoundWithId(result, id);
        return result;
    }

    @Transactional
    public boolean deleteVoteForUser(int voteId, int userId) {
        boolean result = crudVoteRepository.deleteVoteByIdAndUserId(voteId, userId) != 0;
        ValidatorUtil.checkNotFoundWithId(result, voteId);
        return result;
    }

    public List<Vote> getAllByDateBetweenAndUserId(LocalDate start, LocalDate end, int userId) {
        return ValidatorUtil.checkNotFoundForDate(crudVoteRepository.getAllByDateBetweenAndUserId(start, end, userId), start, end);
    }

    public long countResultVote(LocalDate day, int restaurantId) {
        return crudVoteRepository.countVoteByDateAndRestaurantId(day, restaurantId);
    }

    public Vote getMyVoteForDay(LocalDate day, int userId) {
        return ValidatorUtil.checkNotFoundWithId(crudVoteRepository.getVoteByDateAndUserId(day, userId), userId);
    }
}
