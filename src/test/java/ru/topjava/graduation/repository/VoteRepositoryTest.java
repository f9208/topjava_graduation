package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class VoteRepositoryTest extends AbstractStarterTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    VoteRepository voteRepository;

    @Test
    void toVote() {
        LocalDate date = LocalDateTime.now().toLocalDate();
        voteRepository.toVote(date, 10000, 10002);
    }

    @Test
    void getVote() {
        System.out.println(voteRepository.get(10012));
    }

    @Test
    void getAll() {
        System.out.println(voteRepository.findAll());
    }
}