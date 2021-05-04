package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.graduation.Exceptions.NotFoundException;
import ru.topjava.graduation.model.entities.Vote;
import ru.topjava.graduation.repository.testData.UserTestData;

import java.util.List;

import static ru.topjava.graduation.repository.testData.VoteTestData.*;
import static ru.topjava.graduation.repository.testData.UserTestData.*;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.*;


class VoteRepositoryTest extends AbstractStarterTest {
    @Autowired
    VoteRepository voteRepository;

    @Test
    void toVote() {
        Vote voted = voteRepository.toVote(TODAY, ADMIN_ID, MEAT_HOME_ID);
        VOTE_TEST_MATCHER.assertMatch(voteRepository.getAllForUserBetween(TODAY, TODAY, ADMIN_ID), voted);
        VOTE_TEST_MATCHER.assertMatch(voteRepository.get(voted.getId()), voted);
    }

    @Test
    void reVote() {
        Vote alter = voteRepository.getVoteForUserOnDate(ADMIN_ID, START);
        VOTE_TEST_MATCHER.assertMatch(alter, VOTE2);
        Vote revote = voteRepository.toVote(START, ADMIN_ID, MEAT_HOME_ID);
        VOTE_TEST_MATCHER.assertMatch(revote, voteRepository.getVoteForUserOnDate(ADMIN_ID, START));
    }

    @Test
    void get() {
        VOTE_TEST_MATCHER.assertMatch(VOTE1, voteRepository.get(VOTE_1_ID));
    }

    @Test
    void getAll() {
        VOTE_TEST_MATCHER.assertMatch(voteRepository.findAll(), allVotesOfEveryone);
    }

    @Test
    void getAllBetween() {
        VOTE_TEST_MATCHER.assertMatch(List.of(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5),
                voteRepository.getAllBetween(START, END));
    }

    @Test
    void getForSingleDay() {
        VOTE_TEST_MATCHER.assertMatch(List.of(VOTE2, VOTE3, VOTE4, VOTE5),
                voteRepository.getAllBetween(START, START));
    }

    @Test
    void getVoteByIdAndUserId() {
        VOTE_TEST_MATCHER.assertMatch(VOTE3, voteRepository.getVoteByIdAndUserId(VOTE_3_ID, USER_JONNY_ID));
    }

    @Test
    void getNoOwner() {
        assertThrows(NotFoundException.class, () ->voteRepository.getVoteByIdAndUserId(VOTE_3_ID, ADMIN_ID));
    }


    @Test
    void getAllForUser() {
        VOTE_TEST_MATCHER.assertMatch(allVotesOfAdmin, voteRepository.getAllForUser(ADMIN_ID));
    }

    @Test
    void getAllForUserBetween() {
        VOTE_TEST_MATCHER.assertMatch(List.of(VOTE1, VOTE2), voteRepository.getAllForUserBetween(START, END, ADMIN_ID));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> voteRepository.get(VOTE_NOT_FOUND_ID));
    }

    @Test
    void getAllForUserNotFound() {
        assertThrows(NotFoundException.class, () -> voteRepository.get(UserTestData.NOT_FOUND));
    }

    @Test
    void deleteByAdmin() {
        voteRepository.deleteVote(VOTE_1_ID);
        assertThrows(NotFoundException.class, () -> voteRepository.get(VOTE_1_ID));
    }

    @Test
    void deleteByUser() {
        voteRepository.get(VOTE_1_ID);
        voteRepository.deleteVoteForUser(ADMIN_ID, VOTE_1_ID);
        assertThrows(NotFoundException.class, () -> voteRepository.get(VOTE_1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> voteRepository.deleteVote(VOTE_NOT_FOUND_ID));
    }

    @Test
    void getDateNotFound() {
        assertThrows(NotFoundException.class, () -> voteRepository.getAllBetween(TODAY, TODAY));
    }
}