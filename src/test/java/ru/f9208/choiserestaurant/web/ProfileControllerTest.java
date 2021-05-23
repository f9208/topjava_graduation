package ru.f9208.choiserestaurant.web;

import ru.f9208.choiserestaurant.TestUtil;
import ru.f9208.choiserestaurant.repository.testData.UserTestData;
import ru.f9208.choiserestaurant.repository.testData.VoteTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.f9208.choiserestaurant.model.entities.User;
import ru.f9208.choiserestaurant.model.entities.to.VoteTo;
import ru.f9208.choiserestaurant.repository.UserRepository;
import ru.f9208.choiserestaurant.repository.VoteRepository;
import ru.f9208.choiserestaurant.web.AbstractRestControllerTest;
import ru.f9208.choiserestaurant.web.exceptions.NotFoundException;
import ru.f9208.choiserestaurant.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.f9208.choiserestaurant.model.entities.to.VoteTo.convert;
import static ru.f9208.choiserestaurant.web.ProfileController.PROFILE;
import static ru.f9208.choiserestaurant.web.ProfileController.VOTES;

class ProfileControllerTest extends AbstractRestControllerTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    VoteRepository voteRepository;

    private static final String REST_URL = PROFILE;

    @Test
    void getProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.userJonny));
    }

    @Test
    void createProfile() throws Exception {
        User newUser = UserTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().isCreated());
        User created = TestUtil.readFromJson(action, User.class);
        int createdId = created.getId();
        newUser.setId(createdId);
        UserTestData.USER_MATCHER.assertMatch(newUser, created);
        UserTestData.USER_MATCHER.assertMatch(userRepository.findById(createdId), newUser);
    }

    @Test
    void deleteProfile() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andExpect(status().isNoContent());
        UserTestData.USER_MATCHER.assertMatch(userRepository.getAll(), List.of(UserTestData.admin, UserTestData.userKet, UserTestData.userLeo));
    }

    @Test
    void updateUser() throws Exception {
        User updated = UserTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andExpect(status().isNoContent());
        UserTestData.USER_MATCHER.assertMatch(updated, userRepository.findById(UserTestData.getUpdated().getId()));
    }

    @Test
    void getMyVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/votes/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.VOTE_TO_TEST_MATCHER.contentJson(VoteTo.convert(VoteTestData.VOTE14_TODAY)));
    }

    @Test
    void getOneVote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/votes/" + VoteTestData.VOTE_3_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.VOTE_TO_TEST_MATCHER.contentJson(new VoteTo(VoteTestData.VOTE3)));
    }

    @Test
    void getMyVotesFilter() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTES)
                .param("start", "2021-04-21")
                .param("end", "2021-04-26")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.VOTE_TO_TEST_MATCHER.contentJson(convert(VoteTestData.VOTE11, VoteTestData.VOTE7)));
    }

    @Test
    void deleteVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTES + "/" + VoteTestData.VOTE_3_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(String.valueOf(VoteTestData.VOTE_3_ID))
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteRepository.get(VoteTestData.VOTE_3_ID));
    }
}