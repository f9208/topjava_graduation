package ru.topjava.graduation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduation.model.entities.Vote;
import ru.topjava.graduation.utils.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class VoteRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CrudVoteRepository crudVoteRepository;

    public VoteRepository(CrudVoteRepository crudVoteRepository) {
        this.crudVoteRepository = crudVoteRepository;
    }

    @Transactional
    public Vote toVote(LocalDate date, int userId, int restaurantId) {
        log.info("vote for restaurant {} by user {} for {}", restaurantId, userId, date.format(DateTimeFormatter.ISO_DATE));
        if (date == null) date = LocalDateTime.now().toLocalDate();
        Vote savedBeforeVote = crudVoteRepository.findByUserIdAndDate(userId, date);
        if (savedBeforeVote == null) {
            return crudVoteRepository.save(new Vote(null, date, userId, restaurantId));
        } else {
            savedBeforeVote.setRestaurantId(restaurantId);
            return savedBeforeVote;
        }
    }

    public List<Vote> findAll() {
        log.info("get all vote");
        return crudVoteRepository.findAll();
    }

    public Vote get(int id) {
        log.info("get vote {}", id);
        return crudVoteRepository.findById(id).orElse(null);
    }

    public List<Vote> getAllBetween(LocalDate start, LocalDate end) {
        return crudVoteRepository.getAllByDateBetween(start, end);
    }

    public List<Vote> getAllForUser(int userId) {
        return crudVoteRepository.getAllByUserId(userId);
    }

    public List<Vote> getAllForUserBetween(LocalDate start, LocalDate end, int userId) {
        return crudVoteRepository.getAllByDateBetweenAndUserId(start, end, userId);
    }

    public void deleteVote(int id) {
        crudVoteRepository.deleteById(id);
    }

    @Transactional
    public void deleteVoteForUser(int voteId, int userId) {
        crudVoteRepository.deleteVoteByIdAndUserId(voteId, userId);
    }
}
