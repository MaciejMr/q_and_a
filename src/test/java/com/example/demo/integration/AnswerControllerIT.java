package com.example.demo.integration;

import com.example.demo.controller.AnswerController;
import com.example.demo.controller.QuestionController;
import com.example.demo.dto.AddAnswerDTO;
import com.example.demo.dto.AnswersWithCommentsDTO;
import com.example.demo.dto.QuestionWithAnswersAndCommentsDTO;
import com.example.demo.integration.util.IdsDto;
import com.example.demo.integration.util.QuestionUtil;
import com.example.demo.integration.util.RegisteredUserUtil;
import com.example.demo.model.User;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class AnswerControllerIT {

    @Autowired
    private RegisteredUserUtil registeredUserCreator;
    @Autowired
    private QuestionUtil questionUtil;
    @Autowired
    private QuestionController questionController;
    @Autowired
    private AnswerController sut;

    @Test
    void shouldAddNewAnswer_WhenAllInformationCorrect(){
        // given
        User userForQuestion = registeredUserCreator.registerNewUserWith("userForQuestion");
        User userForAnswer = registeredUserCreator.registerNewUserWith("username");
        String content = "content";

        Long qId = questionUtil.addNewQuestion(userForQuestion);

        AddAnswerDTO request = new AddAnswerDTO();
        request.setContent(content);

        // when
        ResponseEntity<?> result = sut.addAnswer(request, qId, userForAnswer);

        // then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        QuestionWithAnswersAndCommentsDTO question = questionController.fullQuestion(qId).getBody();
        List<AnswersWithCommentsDTO> answers = question.getAnswers();
        assertThat(answers, hasSize(1));
        AnswersWithCommentsDTO answer = answers.get(0);
        assertEquals(content, answer.getContents());
        assertEquals(0L, answer.getRating());
        assertEquals(LocalDate.now(), answer.getLocalDate());
        assertThat(answer.getComments(), IsEmptyCollection.empty());
        assertEquals(userForAnswer.getId(), answer.getUser().getId());
        assertEquals(userForAnswer.getDisplayName(), answer.getUser().getDisplayName());
    }

    @Test
    void shouldReturn400_WhenQuestionDoesNotExist(){
        // given
        User user = registeredUserCreator.registerNewUserWith("any");

        // when
        ResponseEntity<?> response = sut.addAnswer(new AddAnswerDTO(), 500L, user);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldAddPositiveVote_WhenAnswerExist(){
        // given
        User userForQuestion = registeredUserCreator.registerNewUserWith("userForQuestion");
        User userForAnswer = registeredUserCreator.registerNewUserWith("userForAnswer");
        User userForVote = registeredUserCreator.registerNewUserWith("userForVote");

        IdsDto idsDto = questionUtil.addNewQuestionWithAnswer(userForQuestion, userForAnswer);

        // when
        ResponseEntity<Void> result = sut.addPositiveVote(idsDto.getQuestionId().toString(), idsDto.getAnswerId(), userForVote);
        // then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        AnswersWithCommentsDTO answer = questionUtil.getFirstAnswerFor(idsDto.getQuestionId());
        assertEquals(1L, answer.getRating());
    }

    @Test
    void shouldReturn400_WhenAnswerForPositiveDoesNotExist(){
        // given
        User user = registeredUserCreator.registerNewUserWith("any");

        // when
        ResponseEntity<Void> response = sut.addPositiveVote("anything", 500L, user);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldAddNegativeVote_WhenAnswerExist(){
        // given
        User userForQuestion = registeredUserCreator.registerNewUserWith("userForQuestion");
        User userForAnswer = registeredUserCreator.registerNewUserWith("userForAnswer");
        User userForVote = registeredUserCreator.registerNewUserWith("userForVote");

        IdsDto idsDto = questionUtil.addNewQuestionWithAnswer(userForQuestion, userForAnswer);

        // when
        ResponseEntity<Void> result = sut.addNegativeVote(idsDto.getQuestionId().toString(), idsDto.getAnswerId(), userForVote);

        // then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        AnswersWithCommentsDTO answer = questionUtil.getFirstAnswerFor(idsDto.getQuestionId());
        assertEquals(-1L, answer.getRating());
    }

    @Test
    void shouldReturn400_WhenAnswerForNegativeDoesNotExist(){
        // given
        User user = registeredUserCreator.registerNewUserWith("any");

        // when
        ResponseEntity<Void> response = sut.addNegativeVote("anything", 500L, user);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
