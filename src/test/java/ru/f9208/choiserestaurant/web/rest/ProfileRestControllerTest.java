package ru.f9208.choiserestaurant.web.rest;

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
import ru.f9208.choiserestaurant.web.exceptions.NotFoundException;
import ru.f9208.choiserestaurant.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.f9208.choiserestaurant.model.entities.to.VoteTo.convert;
import static ru.f9208.choiserestaurant.web.rest.ProfileRestController.PROFILE;
import static ru.f9208.choiserestaurant.web.rest.ProfileRestController.VOTES;

class ProfileRestControllerTest extends AbstractRestControllerTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    VoteRepository voteRepository;

    @Test
    void getProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.userJonny));
    }

    @Test
    void createProfile() throws Exception {
        User newUser = UserTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(PROFILE + "/register")
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
        perform(MockMvcRequestBuilders.delete(PROFILE)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andExpect(status().isNoContent());
        UserTestData.USER_MATCHER.assertMatch(userRepository.getAll(), List.of(UserTestData.admin, UserTestData.userKet, UserTestData.userLeo));
    }

    @Test
    void updateUser() throws Exception {
        User updated = UserTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(PROFILE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andExpect(status().isNoContent());
        UserTestData.USER_MATCHER.assertMatch(updated, userRepository.findById(UserTestData.getUpdated().getId()));
    }

    @Test
    void getMyVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE+VOTES)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.VOTE_TO_TEST_MATCHER.contentJson(VoteTo.convert(VoteTestData.VOTE13_TODAY)));
    }

    @Test
    void getOneVote() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTES +"/"+ VoteTestData.VOTE_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.VOTE_TO_TEST_MATCHER.contentJson(new VoteTo(VoteTestData.VOTE2)));
    }

    @Test
    void getMyVotesFilter() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE+VOTES)
                .param("start", "2021-04-21")
                .param("end", "2021-04-26")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.VOTE_TO_TEST_MATCHER.contentJson(convert(VoteTestData.VOTE10, VoteTestData.VOTE6)));
    }

    @Test
    void deleteVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(PROFILE+VOTES + "/" + VoteTestData.VOTE_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(String.valueOf(VoteTestData.VOTE_2_ID))
                .with(TestUtil.userHttpBasic(UserTestData.userJonny)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteRepository.get(VoteTestData.VOTE_2_ID));
    }
}