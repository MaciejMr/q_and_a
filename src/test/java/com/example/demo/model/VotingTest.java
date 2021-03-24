package com.example.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VotingTest {

    @Test
void shouldUprateByOne_WhenVoteForTheFirstTime() {
    //given
    User user = new User("Matthew","password","matt@email.uk","Matt");
    Answer answer = new Answer(user, "answer");

    //when
    answer.uprate(6L);

    //then
    assertEquals(1L, answer.getRating());
}

    @Test
    void shouldNotChangeRating_WhenVotedForTheSecondTimeInTheSameWay() {
        //given
        User user = new User("Matthew","password","matt@email.uk","Matt");
        Answer answer = new Answer(user, "answer");
        Long userId = 6L;
        answer.uprate(userId);

        //when
        answer.uprate(userId);

        //then
        assertEquals(1L, answer.getRating());
    }

    @Test
    void shouldChangeRating_WhenVotedForTheSecondTimeInDifferentWay() {
        //given
        User user = new User("Matthew","password","matt@email.uk","Matt");
        Answer answer = new Answer(user, "answer");
        Long userId = 6L;
        answer.downrate(userId);

        //when
        answer.uprate(userId);

        //then
        assertEquals(1L, answer.getRating());
    }

    @Test
    void shouldChangeRating_WhenMultipleUsersVotedPositive() {
        //given
        User user = new User("Matthew","password","matt@email.uk","Matt");
        Answer answer = new Answer(user, "answer");
        Long userId1 = 6L;
        Long userId2 = 10L;
        Long userId3 = 2L;
        answer.uprate(userId1);
        answer.uprate(userId2);

        //when
        answer.uprate(userId3);

        //then
        assertEquals(3L, answer.getRating());
    }


    @Test
    void shouldDownrateByOne_WhenVoteForTheFirstTime() {
        //given
        User user = new User("Matthew","password","matt@email.uk","Matt");
        Answer answer = new Answer(user, "answer");

        //when
        answer.downrate(8L);

        //then
        assertEquals(-1L, answer.getRating());
    }

    @Test
    void shouldNotChangeRating_WhenVotedForTheSecondTimeNegative() {
        //given
        User user = new User("Matthew","password","matt@email.uk","Matt");
        Answer answer = new Answer(user, "answer");
        Long userId = 8L;
        answer.downrate(userId);

        //when
        answer.downrate(userId);

        //then
        assertEquals(-1L, answer.getRating());
    }

    @Test
    void shouldChangeRating_WhenVotedForTheSecondTimeInDifferentWayFromPositiveToNegative() {
        //given
        User user = new User("Matthew","password","matt@email.uk","Matt");
        Answer answer = new Answer(user, "answer");
        Long userId = 8L;
        answer.uprate(userId);

        //when
        answer.downrate(userId);

        //then
        assertEquals(-1L, answer.getRating());
    }

    @Test
    void shouldChangeRating_WhenMultipleUsersVotedNegative() {
        //given
        User user = new User("Matthew","password","matt@email.uk","Matt");
        Answer answer = new Answer(user, "answer");
        Long userId1 = 6L;
        Long userId2 = 10L;
        Long userId3 = 2L;
        answer.downrate(userId1);
        answer.downrate(userId2);

        //when
        answer.downrate(userId3);

        //then
        assertEquals(-3L, answer.getRating());
    }

    @Test
    void shouldChangeRating_WhenMultipleUsersVotedNegativeAndPositiveWay() {
        //given
        User user = new User("Matthew","password","matt@email.uk","Matt");
        Answer answer = new Answer(user, "answer");
        Long userId1 = 6L;
        Long userId2 = 10L;
        Long userId3 = 2L;

        answer.downrate(userId1);
        answer.uprate(userId2);

        //when
        answer.downrate(userId3);

        //then
        assertEquals(-1L, answer.getRating());
    }

}