package ru.topjava.graduation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduation.SecurityUtil;
import ru.topjava.graduation.model.entities.Restaurant;
import ru.topjava.graduation.model.entities.User;
import ru.topjava.graduation.model.entities.Vote;
import ru.topjava.graduation.model.entities.to.VoteTo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class VoteRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CrudVoteRepository crudVoteRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public VoteRepository(CrudVoteRepository crudVoteRepository,
                          CrudUserRepository crudUserRepository,
                          CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    //todo сделать здесь валидацию по неустановленной дате
    @Transactional
    public Vote toVote(LocalDateTime date, int userId, int restaurantId) {
        log.info("vote for restaurant {} by user {} for {}", restaurantId, userId, date.format(DateTimeFormatter.ISO_DATE));
        User user = crudUserRepository.getOne(userId);
        Restaurant restaurant = crudRestaurantRepository.getOne(restaurantId);
        return crudVoteRepository.save(new Vote(null, date, user, restaurant));
    }

    public List<Vote> findAll() {
        log.info("get all vote");
        return crudVoteRepository.findAll();
    }

    public List<VoteTo> getAllTo() {
        log.info("get All voteTo");
        return VoteTo.getListVoteTo(crudVoteRepository.findAll());
    }

    public Vote get(int id) {
        log.info("get vote {}", id);
        return crudVoteRepository.findById(id).orElse(null);
    }

    public List<Vote> getAllWithRestaurantAndUser() {
        return crudVoteRepository.getAllWithRestaurantAndUser();
    }

    public Vote getOneWithRestaurantAndUser(int id) {
        log.info("get vote {} with restaurant and user ", id);
        return crudVoteRepository.getVoteWithRestaurantAndUser(id);
    }

    public Vote save(Vote vote) {
        log.info("save voteTo");
        //todo где то здесь должен быть слой проверки возможности голосовать один раз
//        crudVoteRepository.getVoteWithRestaurantAndUser(voteTo.getId());
//        Vote created = new Vote(voteTo, SecurityUtil.getAuthUser());
        return crudVoteRepository.save(vote);
    }
}
