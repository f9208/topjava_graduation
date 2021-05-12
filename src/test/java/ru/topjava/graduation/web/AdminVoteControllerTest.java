package ru.topjava.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.graduation.Exceptions.NotFoundException;
import ru.topjava.graduation.repository.VoteRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduation.TestUtil.userHttpBasic;
import static ru.topjava.graduation.repository.testData.UserTestData.*;
import static ru.topjava.graduation.repository.testData.VoteTestData.*;
import static ru.topjava.graduation.web.AdminVoteController.ADMIN_VOTES;

class AdminVoteControllerTest extends AbstractRestControllerTest {
    @Autowired
    VoteRepository voteRepository;
    private final String REST_URL = ADMIN_VOTES+"/";

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(allVotesOfEveryone));
    }

    @Test
    void getAllForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("user_id", String.valueOf(USER_JONNY_ID))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(allVotesOfJonny));
    }

    @Test
    void getOne() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(VOTE2));
    }

    @Test
    void getFiltered() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("start", "2021-04-22")
                .param("end", "2021-04-25")
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(List.of(VOTE8, VOTE9, VOTE10, VOTE11)));
    }

    @Test
    void getForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "today")
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(List.of(VOTE14_TODAY, VOTE15_TODAY)));
    }

    @Test
    void deleteVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_3_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .param("vote_id", String.valueOf(VOTE_3_ID))
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, () -> voteRepository.get(VOTE_3_ID));
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
                .with(userHttpBasic(userJonny)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}