package ru.topjava.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.f9208.choiserestaurant.model.entities.to.VoteTo;
import ru.f9208.choiserestaurant.repository.VoteRepository;
import ru.f9208.choiserestaurant.web.Exceptions.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.graduation.TestUtil.userHttpBasic;
import static ru.f9208.choiserestaurant.model.entities.to.VoteTo.convert;
import static ru.topjava.graduation.repository.testData.UserTestData.admin;
import static ru.topjava.graduation.repository.testData.UserTestData.userJonny;
import static ru.topjava.graduation.repository.testData.VoteTestData.*;
import static ru.f9208.choiserestaurant.web.AdminVoteController.ADMIN_VOTES;

class AdminVoteControllerTest extends AbstractRestControllerTest {
    @Autowired
    VoteRepository voteRepository;
    private final String REST_URL = ADMIN_VOTES + "/";

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_TEST_MATCHER.contentJson(convert(allVotesOfEveryone)));
    }

    @Test
    void getOne() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_TEST_MATCHER.contentJson(new VoteTo(VOTE2)));
    }

    @Test
    void getFiltered() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("start", "2021-04-22")
                .param("end", "2021-04-25")
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_TEST_MATCHER.contentJson(convert(VOTE8, VOTE9, VOTE10, VOTE11)));
    }

    @Test
    void getForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "today")
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_TEST_MATCHER.contentJson(convert(VOTE14_TODAY, VOTE15_TODAY)));
    }

    @Test
    void deleteVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(VOTE_3_ID))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteRepository.get(VOTE_3_ID));
    }

    @Test
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_3_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(userJonny)))
                .andDo(print())
                .andExpect(status().isForbidden());
        VOTE_TEST_MATCHER.assertMatch(VOTE3, voteRepository.get(VOTE_3_ID));
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