package ru.topjava.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduation.TestUtil;
import ru.f9208.choiserestaurant.model.entities.to.VoteTo;
import ru.f9208.choiserestaurant.repository.VoteRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduation.TestUtil.userHttpBasic;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.BEAR_GRIZZLY_ID;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.MEAT_HOME_ID;
import static ru.topjava.graduation.repository.testData.UserTestData.USER_JONNY_ID;
import static ru.topjava.graduation.repository.testData.UserTestData.userJonny;
import static ru.topjava.graduation.repository.testData.VoteTestData.*;
import static ru.f9208.choiserestaurant.web.ProfileController.VOTES;
import static ru.f9208.choiserestaurant.web.VoteController.RESULTS;

public class VoteControllerTest extends AbstractRestControllerTest {
    private static final String REST_PATH = VOTES;
    @Autowired
    VoteRepository voteRepository;

    @Test
    void toVote() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(MEAT_HOME_ID))
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isCreated());

        VoteTo created = TestUtil.readFromJson(action, VoteTo.class);
        VOTE_TO_TEST_MATCHER.assertMatch(created, new VoteTo(voteRepository.getVoteByIdAndUserId(created.getVoteId(), USER_JONNY_ID)));
    }

    @Test
    void results() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PATH + RESULTS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_RESULTS_TEST_MATCHER.contentJson(resultMeatHome, resultBearGrizzly));
    }

    @Test
    void changeMind() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(MEAT_HOME_ID))
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isCreated());
        VoteTo created = TestUtil.readFromJson(action, VoteTo.class);
        VOTE_TO_TEST_MATCHER.assertMatch(created, new VoteTo(voteRepository.getVoteByIdAndUserId(created.getVoteId(), USER_JONNY_ID)));

        ResultActions action2 = perform(MockMvcRequestBuilders.post(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(BEAR_GRIZZLY_ID))
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isCreated());
        VoteTo created2 = TestUtil.readFromJson(action2, VoteTo.class);
        VOTE_TO_TEST_MATCHER.assertMatch(created2, new VoteTo(voteRepository.getVoteByIdAndUserId(created2.getVoteId(), USER_JONNY_ID)));
    }

    @Test
    void voteUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(MEAT_HOME_ID)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        perform(MockMvcRequestBuilders.get(REST_PATH + RESULTS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(userJonny)))
                .andExpect(status().isOk())
                .andExpect(VOTE_RESULTS_TEST_MATCHER.contentJson(resultMeatHome, resultBearGrizzly));
    }
}
