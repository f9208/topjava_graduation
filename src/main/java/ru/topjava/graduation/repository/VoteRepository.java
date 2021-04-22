package ru.topjava.graduation.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduation.model.entities.Restaurant;
import ru.topjava.graduation.model.entities.User;
import ru.topjava.graduation.model.entities.Vote;
import ru.topjava.graduation.model.entities.to.VoteTo;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VoteRepository {
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
        User user = crudUserRepository.getOne(userId);
        Restaurant restaurant = crudRestaurantRepository.getOne(restaurantId);
        return crudVoteRepository.save(new Vote(null, date, user, restaurant));
    }

    public List<Vote> findAll() {
        return crudVoteRepository.findAll();
    }

    public List<VoteTo> getAllTo() {
        return VoteTo.getListVoteTo(crudVoteRepository.findAll());
    }

    public Vote get(int id) {
        return crudVoteRepository.findById(id).orElse(null);
    }

    public Vote getVoteWithRestaurantAndUser(int id) {
        return crudVoteRepository.getVoteWithRestaurantAndUser(id);
    }
}
