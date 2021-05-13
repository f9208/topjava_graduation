package ru.topjava.graduation.repository.testData;

import ru.topjava.graduation.model.entities.Vote;
import ru.topjava.graduation.model.entities.to.VoteResults;
import ru.topjava.graduation.model.entities.to.VoteTo;
import ru.topjava.graduation.repository.TestMatcher;

import java.time.LocalDate;
import java.util.List;

import static ru.topjava.graduation.model.entities.AbstractNamedEntity.START_SEQ;
import static ru.topjava.graduation.repository.testData.RestaurantTestData.*;
import static ru.topjava.graduation.repository.testData.UserTestData.*;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_TEST_MATCHER = TestMatcher.usingIgnoreFieldsComparator(Vote.class, "restaurant");
    public static final TestMatcher<VoteTo> VOTE_TO_TEST_MATCHER = TestMatcher.usingIgnoreFieldsComparator(VoteTo.class);
    public static final TestMatcher<VoteResults> VOTE_RESULTS_TEST_MATCHER = TestMatcher.usingIgnoreFieldsComparator(VoteResults.class);

    public static final LocalDate START = LocalDate.parse("2020-04-09");
    public static final LocalDate END = LocalDate.parse("2020-04-10");
    public static final LocalDate TODAY = LocalDate.now();
    public static final LocalDate DATE_NOT_FOUND = LocalDate.parse("2020-01-01");

    public static final int VOTE_1_ID = START_SEQ;
    public static final int VOTE_2_ID = START_SEQ + 1;
    public static final int VOTE_3_ID = START_SEQ + 2;
    public static final int VOTE_4_ID = START_SEQ + 3;
    public static final int VOTE_5_ID = START_SEQ + 4;
    public static final int VOTE_6_ID = START_SEQ + 5;
    public static final int VOTE_7_ID = START_SEQ + 6;
    public static final int VOTE_8_ID = START_SEQ + 7;
    public static final int VOTE_9_ID = START_SEQ + 8;
    public static final int VOTE_10_ID = START_SEQ + 9;
    public static final int VOTE_11_ID = START_SEQ + 10;
    public static final int VOTE_12_ID = START_SEQ + 11;
    public static final int VOTE_13_ID = START_SEQ + 12;
    public static final int VOTE_14_ID = START_SEQ + 13;
    public static final int VOTE_15_ID = START_SEQ + 14;
    public static final int VOTE_NOT_FOUND_ID = 10;

    public static Vote VOTE1 = new Vote(VOTE_1_ID, END, ADMIN_ID, bearGrizzly);
    public static Vote VOTE2 = new Vote(VOTE_2_ID, START, ADMIN_ID, bearGrizzly);
    public static Vote VOTE3 = new Vote(VOTE_3_ID, START, USER_JONNY_ID, bearGrizzly);
    public static Vote VOTE4 = new Vote(VOTE_4_ID, START, USER_KET_ID, bearGrizzly);
    public static Vote VOTE5 = new Vote(VOTE_5_ID, START, USER_LEO_ID, meatHome);
    public static Vote VOTE6 = new Vote(VOTE_6_ID, LocalDate.parse("2021-04-26"), ADMIN_ID, meatHome);
    public static Vote VOTE7 = new Vote(VOTE_7_ID, LocalDate.parse("2021-04-26"), USER_JONNY_ID, meatHome);
    public static Vote VOTE8 = new Vote(VOTE_8_ID, LocalDate.parse("2021-04-25"), ADMIN_ID, meatHome);
    public static Vote VOTE9 = new Vote(VOTE_9_ID, LocalDate.parse("2021-04-25"), USER_KET_ID, meatHome);
    public static Vote VOTE10 = new Vote(VOTE_10_ID, LocalDate.parse("2021-04-24"), ADMIN_ID, bearGrizzly);
    public static Vote VOTE11 = new Vote(VOTE_11_ID, LocalDate.parse("2021-04-22"), USER_JONNY_ID, meatHome);
    public static Vote VOTE12 = new Vote(VOTE_12_ID, LocalDate.parse("2021-04-21"), ADMIN_ID, bearGrizzly);
    public static Vote VOTE13 = new Vote(VOTE_13_ID, LocalDate.parse("2021-04-27"), USER_LEO_ID, meatHome);
    public static Vote VOTE14_TODAY = new Vote(VOTE_14_ID, LocalDate.now(), USER_JONNY_ID, bearGrizzly);
    public static Vote VOTE15_TODAY = new Vote(VOTE_15_ID, LocalDate.now(), ADMIN_ID, meatHome);

    public static List<Vote> allVotesOfEveryone = List.of(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, VOTE7, VOTE8,
            VOTE9, VOTE10, VOTE11, VOTE12, VOTE13, VOTE14_TODAY, VOTE15_TODAY);
    public static List<Vote> allVotesOfAdmin = List.of(VOTE1, VOTE2, VOTE6, VOTE8, VOTE10, VOTE12, VOTE15_TODAY);
    public static List<Vote> allVotesOfJonny = List.of(VOTE3, VOTE7, VOTE11, VOTE14_TODAY);

    public static VoteResults resultMeatHome = new VoteResults(MEAT_HOME_ID, 1);
    public static VoteResults resultBearGrizzly = new VoteResults(BEAR_GRIZZLY_ID, 1);
}

