package ru.f9208.choiserestaurant.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.f9208.choiserestaurant.TestUtil;
import ru.f9208.choiserestaurant.model.entities.to.VoteTo;
import ru.f9208.choiserestaurant.repository.VoteRepository;
import ru.f9208.choiserestaurant.repository.testData.UserTestData;
import ru.f9208.choiserestaurant.repository.testData.VoteTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.f9208.choiserestaurant.model.entities.to.VoteTo.convert;
import static ru.f9208.choiserestaurant.web.rest.AdminVoteRestController.ADMIN_VOTES;

class AdminVoteRestControllerTest extends AbstractRestControllerTest {
    @Autowired
    VoteRepository voteRepository;
    private final String REST_URL = ADMIN_VOTES + "/";

    @Test
    void getOne() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VoteTestData.VOTE_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.VOTE_TO_TEST_MATCHER.contentJson(new VoteTo(VoteTestData.VOTE2)));
    }

    @Test
    void getWithParam() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("start", "2021-04-22")
                .param("end", "2021-04-25")
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.VOTE_TO_TEST_MATCHER.contentJson(convert(VoteTestData.VOTE8, VoteTestData.VOTE9, VoteTestData.VOTE10, VoteTestData.VOTE11)));
    }

    @Test
    void getForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "today")
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.VOTE_TO_TEST_MATCHER.contentJson(convert(VoteTestData.VOTE14_TODAY, VoteTestData.VOTE15_TODAY)));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void getVotesForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("userId", String.valueOf(UserTestData.USER_JONNY_ID))
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_TO_TEST_MATCHER.contentJson(VoteTo.convert(VoteTestData.VOTE14_TODAY)));
    }
}