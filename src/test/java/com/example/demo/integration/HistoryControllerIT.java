package com.example.demo.integration;

import com.example.demo.controller.AnswerController;
import com.example.demo.controller.CommentController;
import com.example.demo.controller.HistoryController;
import com.example.demo.controller.QuestionController;
import com.example.demo.dto.*;
import com.example.demo.integration.util.IdsDto;
import com.example.demo.integration.util.QuestionUtil;
import com.example.demo.integration.util.RegisteredUserUtil;
import com.example.demo.model.User;
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
class HistoryControllerIT {

    @Autowired
    private RegisteredUserUtil registeredUserCreator;
    @Autowired
    private QuestionUtil questionUtil;
    @Autowired
    private HistoryController sut;
    @Autowired
    private QuestionController questionController;
    @Autowired
    private AnswerController answerController;
    @Autowired
    private CommentController commentController;

    @Test
    void shouldReturnAllQuestionAskedByUser_WhenThereAreQuestionsFromMultipleUsers(){
        // given
        User user = registeredUserCreator.registerNewUserWith("user1");
        User otherUser1 = registeredUserCreator.registerNewUserWith("user2");
        User otherUser2 = registeredUserCreator.registerNewUserWith("user3");

        questionUtil.addNewQuestion(user);
        questionUtil.addNewQuestion(user);
        questionUtil.addNewQuestion(user);
        questionUtil.addNewQuestion(otherUser1);
        questionUtil.addNewQuestion(otherUser1);
        questionUtil.addNewQuestion(otherUser2);
        questionUtil.addNewQuestion(otherUser2);
        questionUtil.addNewQuestion(otherUser2);
        questionUtil.addNewQuestion(otherUser2);

        // when
        ResponseEntity<List<QuestionHistoryDTO>> result = sut.questionsHistory(user);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        List<QuestionHistoryDTO> history = result.getBody();
        assertThat(history, hasSize(3));
    }

    @Test
    void shouldReturnCorrectHistoryDetails_WhenThereAreQuestionsFromMultipleUsers(){
        // given
        User user = registeredUserCreator.registerNewUserWith("user1");

        String category = "cat1";
        String content = "content";
        AddQuestionDTO addQuestionDTO = new AddQuestionDTO();
        addQuestionDTO.setCategory(category);
        addQuestionDTO.setContent(content);
        Long qId = questionController.addQuestion(addQuestionDTO, user).getBody();

        // when
        ResponseEntity<List<QuestionHistoryDTO>> result = sut.questionsHistory(user);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        QuestionHistoryDTO historyItem = result.getBody().get(0);
        assertEquals(qId, historyItem.getId());
        assertEquals(LocalDate.now(), historyItem.getLocalDate());
        assertEquals(category, historyItem.getCategory());
        assertEquals(content, historyItem.getContents());
    }

    @Test
    void shouldReturnAllAnswersByUser_WhenThereAreAnswersFromMultipleUsers(){
        // given
        User user = registeredUserCreator.registerNewUserWith("user1");
        User otherUser1 = registeredUserCreator.registerNewUserWith("user2");
        User otherUser2 = registeredUserCreator.registerNewUserWith("user3");

        AddAnswerDTO anyAnswer = new AddAnswerDTO();
        anyAnswer.setContent("anything");

        IdsDto idsDto1 = questionUtil.addNewQuestionWithAnswer(user, otherUser1);
        answerController.addAnswer(anyAnswer, idsDto1.getQuestionId(), user);
        answerController.addAnswer(anyAnswer, idsDto1.getQuestionId(), otherUser1);
        answerController.addAnswer(anyAnswer, idsDto1.getQuestionId(), otherUser2);

        IdsDto idsDto2 = questionUtil.addNewQuestionWithAnswer(otherUser1, otherUser2);
        answerController.addAnswer(anyAnswer, idsDto2.getQuestionId(), user);
        answerController.addAnswer(anyAnswer, idsDto2.getQuestionId(), otherUser1);

        IdsDto idsDto3 = questionUtil.addNewQuestionWithAnswer(otherUser2, user);
        answerController.addAnswer(anyAnswer, idsDto3.getQuestionId(), user);
        answerController.addAnswer(anyAnswer, idsDto3.getQuestionId(), otherUser1);
        answerController.addAnswer(anyAnswer, idsDto3.getQuestionId(), otherUser2);

        // when
        ResponseEntity<List<AnswerHistoryDTO>> result = sut.answersHistory(user);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        List<AnswerHistoryDTO> history = result.getBody();
        assertThat(history, hasSize(4));
    }

    @Test
    void shouldReturnCorrectHistoryDetails_WhenThereAreAnswersFromMultipleUsers(){
        // given
        User user = registeredUserCreator.registerNewUserWith("user1");
        User otherUser1 = registeredUserCreator.registerNewUserWith("user2");

        Long qId = questionUtil.addNewQuestion(otherUser1);
        String content = "content";
        AddAnswerDTO addAnswerDTO = new AddAnswerDTO();
        addAnswerDTO.setContent(content);
        answerController.addAnswer(addAnswerDTO, qId, user);

        // when
        ResponseEntity<List<AnswerHistoryDTO>> result = sut.answersHistory(user);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        AnswerHistoryDTO historyItem = result.getBody().get(0);
        assertEquals(qId, historyItem.getQuestionId());
        assertEquals(LocalDate.now(), historyItem.getLocalDate());
        assertEquals(0L, historyItem.getRating());
        assertEquals(content, historyItem.getContents());
    }

    @Test
    void shouldReturnAllCommentsByUser_WhenThereAreCommentsFromMultipleUsers(){
        // given
        User user = registeredUserCreator.registerNewUserWith("user1");
        User otherUser1 = registeredUserCreator.registerNewUserWith("user2");
        User otherUser2 = registeredUserCreator.registerNewUserWith("user3");

        AddCommentDTO anyComment = new AddCommentDTO();
        anyComment.setContent("anything");

        IdsDto idsDto1 = questionUtil.addNewQuestionWithAnswer(user, otherUser1);
        commentController.addComment(anyComment, idsDto1.getQuestionId(), idsDto1.getAnswerId(), user);
        commentController.addComment(anyComment, idsDto1.getQuestionId(), idsDto1.getAnswerId(), otherUser1);
        commentController.addComment(anyComment, idsDto1.getQuestionId(), idsDto1.getAnswerId(), otherUser2);
        commentController.addComment(anyComment, idsDto1.getQuestionId(), idsDto1.getAnswerId(), user);


        IdsDto idsDto2 = questionUtil.addNewQuestionWithAnswer(otherUser1, otherUser2);
        commentController.addComment(anyComment, idsDto2.getQuestionId(), idsDto1.getAnswerId(), user);
        commentController.addComment(anyComment, idsDto2.getQuestionId(), idsDto1.getAnswerId(), otherUser1);

        IdsDto idsDto3 = questionUtil.addNewQuestionWithAnswer(otherUser2, user);
        commentController.addComment(anyComment, idsDto3.getQuestionId(), idsDto1.getAnswerId(), user);
        commentController.addComment(anyComment, idsDto3.getQuestionId(), idsDto1.getAnswerId(), otherUser1);
        commentController.addComment(anyComment, idsDto3.getQuestionId(), idsDto1.getAnswerId(), otherUser2);

        // when
        ResponseEntity<List<CommentHistoryDTO>> result = sut.commentsHistory(user);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        List<CommentHistoryDTO> history = result.getBody();
        assertThat(history, hasSize(4));
    }

    @Test
    void shouldReturnCorrectHistoryDetails_WhenThereAreCommentsFromMultipleUsers(){
        // given
        User user = registeredUserCreator.registerNewUserWith("user1");
        User otherUser1 = registeredUserCreator.registerNewUserWith("user2");

        String content = "content";
        AddCommentDTO anyComment = new AddCommentDTO();
        anyComment.setContent(content);

        IdsDto idsDto1 = questionUtil.addNewQuestionWithAnswer(user, otherUser1);
        commentController.addComment(anyComment, idsDto1.getQuestionId(), idsDto1.getAnswerId(), user);

        // when
        ResponseEntity<List<CommentHistoryDTO>> result = sut.commentsHistory(user);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        CommentHistoryDTO historyItem = result.getBody().get(0);
        assertEquals(idsDto1.getQuestionId(), historyItem.getQuestionId());
        assertEquals(LocalDate.now(), historyItem.getLocalDate());
        assertEquals(content, historyItem.getContents());
    }
}