package ru.topjava.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduation.Exceptions.NotFoundException;
import ru.topjava.graduation.TestUtil;
import ru.topjava.graduation.model.entities.Vote;
import ru.topjava.graduation.repository.VoteRepository;
import ru.topjava.graduation.utils.SecurityUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduation.TestUtil.userHttpBasic;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.BEAR_GRIZZLY_ID;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.MEAT_HOME_ID;
import static ru.topjava.graduation.repository.testData.UserTestData.USER_JONNY_ID;
import static ru.topjava.graduation.repository.testData.UserTestData.userJonny;
import static ru.topjava.graduation.repository.testData.VoteTestData.*;
import static ru.topjava.graduation.web.VoteController.VOTES;

public class VoteControllerTest extends AbstractRestControllerTest {
    private static final String REST_PATH = VOTES + "/";
    @Autowired
    VoteRepository voteRepository;

    @Test
    void getMyVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(allVotesOfJonny));
    }

    @Test
    void getOne() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PATH + VOTE_3_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(VOTE3));
    }

    @Test
    void getForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PATH + "today")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(List.of(VOTE14_TODAY)));
    }

    @Test
    void getMyVotesFilter() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PATH + "filter")
                .param("start", "2021-04-21")
                .param("end", "2021-04-26")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(List.of(VOTE11, VOTE7)));
    }

    @Test
    void changeMind() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(MEAT_HOME_ID))
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isCreated());
        Vote created = TestUtil.readFromJson(action, Vote.class);
        VOTE_TEST_MATCHER.assertMatch(created, voteRepository.getVoteByIdAndUserId(created.getId(), USER_JONNY_ID));

        ResultActions action2 = perform(MockMvcRequestBuilders.post(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(BEAR_GRIZZLY_ID))
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isCreated());
        Vote created2 = TestUtil.readFromJson(action2, Vote.class);
        VOTE_TEST_MATCHER.assertMatch(created2, voteRepository.getVoteByIdAndUserId(created.getId(), USER_JONNY_ID));
    }

    @Test
    void toVote() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(MEAT_HOME_ID))
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = TestUtil.readFromJson(action, Vote.class);
        VOTE_TEST_MATCHER.assertMatch(created, voteRepository.getVoteByIdAndUserId(created.getId(), USER_JONNY_ID));
    }

    @Test
    void deleteVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(String.valueOf(VOTE_3_ID))
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteRepository.get(VOTE_3_ID));
    }
}
