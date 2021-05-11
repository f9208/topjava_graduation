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
import static ru.topjava.graduation.repository.testData.UserTestData.ADMIN_ID;
import static ru.topjava.graduation.repository.testData.VoteTestData.*;

class AdminVoteControllerTest extends AbstractRestControllerTest {
    @Autowired
    VoteRepository voteRepository;
    private final String REST_PATH = "/admin/votes/";

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(allVotesOfEveryone));

    }

    @Test
    void getAllForAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PATH)
                .param("user_id", String.valueOf(ADMIN_ID))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(allVotesOfAdmin));
    }

    @Test
    void getOne() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PATH + VOTE_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(VOTE2));
    }

    @Test
    void getFiltered() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PATH + "filter")
                .param("start", "2021-04-22")
                .param("end", "2021-04-25"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(List.of(VOTE8, VOTE9, VOTE10, VOTE11)));
    }

    @Test
    void getForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PATH + "today"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TEST_MATCHER.contentJson(List.of(VOTE14_TODAY, VOTE15_TODAY)));
    }

    @Test
    void deleteVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_PATH + VOTE_3_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .param("vote_id", String.valueOf(VOTE_3_ID)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, () -> voteRepository.get(VOTE_3_ID));
    }
}