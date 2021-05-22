package ru.f9208.choiserestaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.f9208.choiserestaurant.TestUtil;
import ru.f9208.choiserestaurant.model.entities.to.VoteTo;
import ru.f9208.choiserestaurant.repository.VoteRepository;
import ru.f9208.choiserestaurant.repository.testData.RestaurantTestData;
import ru.f9208.choiserestaurant.repository.testData.UserTestData;
import ru.f9208.choiserestaurant.repository.testData.VoteTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.f9208.choiserestaurant.web.ProfileController.VOTES;

public class VoteControllerTest extends AbstractRestControllerTest {
    private static final String REST_PATH = VOTES;
    @Autowired
    VoteRepository voteRepository;

    @Test
    void toVote() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(RestaurantTestData.MEAT_HOME_ID))
                .with(TestUtil.userHttpBasic(UserTestData.userKet)))
                .andDo(print())
                .andExpect(status().isCreated());
        VoteTo created = TestUtil.readFromJson(action, VoteTo.class);
        VoteTestData.VOTE_TO_TEST_MATCHER.assertMatch(created, new VoteTo(voteRepository.getVoteByIdAndUserId(created.getVoteId(), UserTestData.USER_KET_ID)));
    }

    @Test
    void voteAndChangeMind() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(RestaurantTestData.MEAT_HOME_ID))
                .with(TestUtil.userHttpBasic(UserTestData.userKet)))
                .andDo(print())
                .andExpect(status().isCreated());
        VoteTo created = TestUtil.readFromJson(action, VoteTo.class);
        VoteTestData.VOTE_TO_TEST_MATCHER.assertMatch(created, new VoteTo(voteRepository.getVoteByIdAndUserId(created.getVoteId(), UserTestData.USER_KET_ID)));

        created.setRestaurantId(RestaurantTestData.BEAR_GRIZZLY_ID);
        perform(MockMvcRequestBuilders.put(REST_PATH + "/" + created.getVoteId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(RestaurantTestData.BEAR_GRIZZLY_ID))
                .with(TestUtil.userHttpBasic(UserTestData.userKet)))
                .andDo(print())
                .andExpect(status().isNoContent());
        VoteTestData.VOTE_TO_TEST_MATCHER.assertMatch(created, new VoteTo(voteRepository.getVoteByIdAndUserId(created.getVoteId(), UserTestData.USER_KET_ID)));
    }

    @Test
    void changeMind() throws Exception {
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE14_TODAY, voteRepository.get(VoteTestData.VOTE_14_ID));
        perform(MockMvcRequestBuilders.put(REST_PATH + "/" + VoteTestData.VOTE_14_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(RestaurantTestData.MEAT_HOME_ID))
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isNoContent());
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE14_TODAY_RE_VOTE, voteRepository.get(VoteTestData.VOTE_14_ID));
    }

    @Test
    void reVoteNotOwner() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_PATH + "/" + VoteTestData.VOTE_14_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(RestaurantTestData.MEAT_HOME_ID))
                .with(TestUtil.userHttpBasic(UserTestData.userKet)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        VoteTestData.VOTE_TEST_MATCHER.assertMatch(VoteTestData.VOTE14_TODAY, voteRepository.get(VoteTestData.VOTE_14_ID));
    }
}
