package ru.f9208.choiserestaurant.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.f9208.choiserestaurant.web.exceptions.NotFoundException;
import ru.f9208.choiserestaurant.model.entities.Vote;
import ru.f9208.choiserestaurant.repository.testData.RestaurantTestData;
import ru.f9208.choiserestaurant.repository.testData.UserTestData;
import ru.f9208.choiserestaurant.repository.testData.VoteTestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.f9208.choiserestaurant.repository.testData.UserTestData.*;
import static ru.f9208.choiserestaurant.repository.testData.VoteTestData.*;


class VoteRepositoryTest extends AbstractStarterTest {
    @Autowired
    VoteRepository voteRepository;

    @Test
    void toVote() {
        Vote voted = voteRepository.toVote(UserTestData.USER_LEO_ID, RestaurantTestData.MEAT_HOME_ID);
        int id = voted.getId();
        VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE16_TODAY_NOT_INCLUDE, voted);
        VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE16_TODAY_NOT_INCLUDE, voteRepository.get(id));
    }

    @Test
    void reVote() {
        Vote alter = voteRepository.getVoteForUserOnDate(UserTestData.USER_JONNY_ID, VoteTestData.TODAY);
        VOTE_TEST_MATCHER.assertMatch(VOTE13_TODAY, alter);
        Vote reVote = voteRepository.reVote(alter.getId(), UserTestData.USER_JONNY_ID, RestaurantTestData.MEAT_HOME_ID);
        VOTE_TEST_MATCHER.assertMatch(reVote, voteRepository.getVoteForUserOnDate(UserTestData.USER_JONNY_ID, VoteTestData.TODAY));
    }

    @Test
    void get() {
        VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE1, voteRepository.get(VoteTestData.VOTE_1_ID));
    }

    @Test
    void getAll() {
        VOTE_TEST_MATCHER.assertMatch(VoteTestData.allVotesOfEveryone, voteRepository.findAll());
    }

    @Test
    void getAllBetween() {
        VOTE_TEST_MATCHER.assertMatch(List.of(VoteTestData.VOTE0, VoteTestData.VOTE1, VoteTestData.VOTE2, VoteTestData.VOTE3, VoteTestData.VOTE4),
                voteRepository.getAllBetween(VoteTestData.START, VoteTestData.END));
    }

    @Test
    void getForSingleDay() {
        VOTE_TEST_MATCHER.assertMatch(List.of(VoteTestData.VOTE1, VoteTestData.VOTE2, VoteTestData.VOTE3, VoteTestData.VOTE4),
                voteRepository.getAllBetween(VoteTestData.START, VoteTestData.START));
    }

    @Test
    void getVoteByIdAndUserId() {
        VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE2, voteRepository.getVoteByIdAndUserId(VoteTestData.VOTE_2_ID, UserTestData.USER_JONNY_ID));
    }

    @Test
    void getNoOwner() {
        assertThrows(NotFoundException.class, () -> voteRepository.getVoteByIdAndUserId(VoteTestData.VOTE_2_ID, UserTestData.ADMIN_ID));
    }

    @Test
    void getAllForUser() {
        VOTE_TEST_MATCHER.assertMatch(VoteTestData.allVotesOfAdmin, voteRepository.getAllForUser(UserTestData.ADMIN_ID));
    }

    @Test
    void getAllForUserBetween() {
        VOTE_TEST_MATCHER.assertMatch(List.of(VoteTestData.VOTE0, VoteTestData.VOTE1), voteRepository.getAllByDateBetweenAndUserId(VoteTestData.START, VoteTestData.END, UserTestData.ADMIN_ID));
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
        voteRepository.get(VoteTestData.VOTE_0_ID);
        voteRepository.deleteVoteForUser(UserTestData.ADMIN_ID, VoteTestData.VOTE_0_ID);
        assertThrows(NotFoundException.class, () -> voteRepository.get(VoteTestData.VOTE_0_ID));
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
        assertThrows(NotFoundException.class, () -> voteRepository.reVote(VoteTestData.VOTE_13_ID, UserTestData.USER_KET_ID, RestaurantTestData.MEAT_HOME_ID));
    }

    @Test
    void getVoteByUserIdToday() {
        VOTE_TEST_MATCHER.assertMatch(voteRepository.getVoteByUserIdToday(USER_KET_ID), VOTE15_TODAY);
        VOTE_TEST_MATCHER.assertMatch(voteRepository.getVoteByUserIdToday(ADMIN_ID), VOTE14_TODAY);
    }

    @Test
    void getVoteByUserIdTodayNoVoted() {
        Object a = voteRepository.getVoteByUserIdToday(USER_LEO_ID);
        assertNull(a);
    }
}