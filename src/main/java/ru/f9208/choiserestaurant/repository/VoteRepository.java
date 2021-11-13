package ru.f9208.choiserestaurant.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.model.entities.Vote;
import ru.f9208.choiserestaurant.utils.ValidatorUtil;
import ru.f9208.choiserestaurant.web.exceptions.IllegalRequestDataException;

import java.time.LocalDate;
import java.util.List;

import static ru.f9208.choiserestaurant.utils.ValidatorUtil.assureVoteOwner;
import static ru.f9208.choiserestaurant.utils.ValidatorUtil.checkNotFoundWithId;


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
    public Vote toVote(int userId, int restaurantId) {
        log.info("voteId for restaurant {} by user {}", restaurantId, userId);
        if (crudVoteRepository.findByUserIdAndDay(userId, LocalDate.now()) == null) {
            Restaurant currentRestaurant = restaurantRepository.getOne(restaurantId);
            return crudVoteRepository.save(new Vote(null, LocalDate.now(), userId, currentRestaurant));
        } else {
            throw new IllegalRequestDataException("Failed create new vote. User with id=" + userId + " has already voted today, try re-vote");
        }
    }

    @Transactional
    public Vote reVote(int voteId, int userId, int restaurantId) {
        log.info("re-vote for restaurant {} by user {}", restaurantId, userId);
        Vote previous = crudVoteRepository.findById(voteId).orElseThrow();
        checkNotFoundWithId(previous, voteId);
        assureVoteOwner(previous, userId);
        Restaurant currentRestaurant = restaurantRepository.getOne(restaurantId);
        previous.setRestaurant(currentRestaurant);
        return previous;
    }

    public List<Vote> findAll() {
        log.info("get all voteId");
        return crudVoteRepository.findAll();
    }

    public Vote get(int id) {
        log.info("get voteId {}", id);
        return ValidatorUtil.checkNotFoundWithId(crudVoteRepository.findById(id).orElse(null), id);
    }

    public Vote getVoteByIdAndUserId(int voteId, int userId) {
        return ValidatorUtil.checkNotFoundWithId(crudVoteRepository.findByIdAndUserId(voteId, userId), voteId);
    }

    public List<Vote> getAllBetween(LocalDate start, LocalDate end) {
        return ValidatorUtil.checkNotFoundForDate(crudVoteRepository.getAllByDayBetween(start, end), start, end);
    }

    public List<Vote> getAllForUser(int userId) {
        return ValidatorUtil.checkNotFoundWithId(crudVoteRepository.getAllByUserId(userId), userId);
    }

    public List<Vote> getAllForRestaurantBetween(LocalDate start, LocalDate end, int restaurantId) {
        return crudVoteRepository.getAllByDayBetweenAndRestaurantId(start, end, restaurantId);
    }

    public Vote getVoteForUserOnDate(int userId, LocalDate date) {
        return ValidatorUtil.checkNotFoundWithId(crudVoteRepository.getVoteByUserIdAndDay(userId, date), userId);
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
        return ValidatorUtil.checkNotFoundForDate(crudVoteRepository.getAllByDayBetweenAndUserId(start, end, userId), start, end);
    }

    public Vote getVoteByUserIdToday(int userId) {
        return crudVoteRepository.getVoteByUserIdOnDay(userId, LocalDate.now());
    }
}
