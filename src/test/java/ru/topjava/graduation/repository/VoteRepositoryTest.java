package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.graduation.model.entities.to.VoteTo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

class VoteRepositoryTest extends AbstractStarterTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    VoteRepository voteRepository;

    @Test
    void toVote() {
        LocalDateTime date = LocalDateTime.now();
        LocalTime time = LocalTime.from(date);
        System.out.println(time.isAfter(LocalTime.parse("11:00:00")));
        voteRepository.toVote(date, 10000, 10002);
        System.out.println(VoteTo.getListVoteTo(voteRepository.findAll()));
    }

    @Test
    void transferObject() {
        System.out.println(VoteTo.getListVoteTo(voteRepository.findAll()));
    }

    @Test
    void getVoteWithRestaurantAndUser() {
        System.out.println(voteRepository.getVoteWithRestaurantAndUser(10010));
    }

    @Test
    void getVote(){
        System.out.println(voteRepository.get(10010));
    }
}