package ru.f9208.choicerestaurant.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.f9208.choicerestaurant.web.exceptions.NotFoundException;
import ru.f9208.choicerestaurant.model.entities.Vote;
import ru.f9208.choicerestaurant.repository.testData.RestaurantTestData;
import ru.f9208.choicerestaurant.repository.testData.UserTestData;
import ru.f9208.choicerestaurant.repository.testData.VoteTestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class VoteRepositoryTest extends AbstractStarterTest {
    @Autowired
    VoteRepository voteRepository;

    @Test
    void toVote() {
        Vote voted = voteRepository.toVote(UserTestData.USER_KET_ID, RestaurantTestData.MEAT_HOME_ID);
        int id = voted.getId();
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE16_TODAY, voted);
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE16_TODAY, voteRepository.get(id));
    }

    @Test
    void reVote() {
        Vote alter = voteRepository.getVoteForUserOnDate(UserTestData.USER_JONNY_ID, VoteTestData.TODAY);
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE14_TODAY, alter);
        Vote reVote = voteRepository.reVote(alter.getId(), UserTestData.USER_JONNY_ID, RestaurantTestData.MEAT_HOME_ID);
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(reVote, voteRepository.getVoteForUserOnDate(UserTestData.USER_JONNY_ID, VoteTestData.TODAY));
    }

    @Test
    void get() {
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE1, voteRepository.get(VoteTestData.VOTE_1_ID));
    }

    @Test
    void getAll() {
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(VoteTestData.allVotesOfEveryone, voteRepository.findAll());
    }

    @Test
    void getAllBetween() {
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(List.of(VoteTestData.VOTE1, VoteTestData.VOTE2, VoteTestData.VOTE3, VoteTestData.VOTE4, VoteTestData.VOTE5),
                voteRepository.getAllBetween(VoteTestData.START, VoteTestData.END));
    }

    @Test
    void getForSingleDay() {
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(List.of(VoteTestData.VOTE2, VoteTestData.VOTE3, VoteTestData.VOTE4, VoteTestData.VOTE5),
                voteRepository.getAllBetween(VoteTestData.START, VoteTestData.START));
    }

    @Test
    void getVoteByIdAndUserId() {
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE3, voteRepository.getVoteByIdAndUserId(VoteTestData.VOTE_3_ID, UserTestData.USER_JONNY_ID));
    }

    @Test
    void getNoOwner() {
        assertThrows(NotFoundException.class, () -> voteRepository.getVoteByIdAndUserId(VoteTestData.VOTE_3_ID, UserTestData.ADMIN_ID));
    }

    @Test
    void getAllForUser() {
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(VoteTestData.allVotesOfAdmin, voteRepository.getAllForUser(UserTestData.ADMIN_ID));
    }

    @Test
    void getAllForUserBetween() {
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(List.of(VoteTestData.VOTE1, VoteTestData.VOTE2), voteRepository.getAllByDateBetweenAndUserId(VoteTestData.START, VoteTestData.END, UserTestData.ADMIN_ID));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> voteRepository.get(VoteTestData.VOTE_NOT_FOUND_ID));
    }

    @Test
    void getAllForUserNotFound() {
        assertThrows(NotFoundException.class, () -> voteRepository.get(UserTestData.NOT_FOUND));
    }

    @Test
    void deleteByUser() {
        voteRepository.get(VoteTestData.VOTE_1_ID);
        voteRepository.deleteVoteForUser(UserTestData.ADMIN_ID, VoteTestData.VOTE_1_ID);
        assertThrows(NotFoundException.class, () -> voteRepository.get(VoteTestData.VOTE_1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> voteRepository.deleteVote(VoteTestData.VOTE_NOT_FOUND_ID));
    }

    @Test
    void getDateNotFound() {
        assertThrows(NotFoundException.class, () -> voteRepository.getAllBetween(VoteTestData.DATE_NOT_FOUND, VoteTestData.DATE_NOT_FOUND));
    }

    @Test
    void reVoteNotOwner() {
        assertThrows(NotFoundException.class, () -> voteRepository.reVote(VoteTestData.VOTE_14_ID, UserTestData.USER_KET_ID, RestaurantTestData.MEAT_HOME_ID));
    }
}