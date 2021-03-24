package com.example.demo.integration;

import com.example.demo.controller.CommentController;
import com.example.demo.dto.AddCommentDTO;
import com.example.demo.dto.AnswersWithCommentsDTO;
import com.example.demo.dto.CommentsDTO;
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
class CommentControllerIT {

    @Autowired
    private RegisteredUserUtil registeredUserCreator;
    @Autowired
    private QuestionUtil questionUtil;
    @Autowired
    private CommentController sut;

    @Test
    void shouldAddNewComment_WhenAllInformationCorrect(){
        // given
        User userForQuestion = registeredUserCreator.registerNewUserWith("userForQuestion");
        User userForAnswer = registeredUserCreator.registerNewUserWith("userForAnswer");
        User userForComment = registeredUserCreator.registerNewUserWith("userForComment");

        String content = "content";
        IdsDto ids = questionUtil.addNewQuestionWithAnswer(userForQuestion, userForAnswer);

        AddCommentDTO request = new AddCommentDTO();
        request.setContent(content);

        // when
        ResponseEntity<Void> result = sut.addComment(request, ids.getQuestionId(), ids.getAnswerId(), userForComment);

        // then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        AnswersWithCommentsDTO answer = questionUtil.getFirstAnswerFor(ids.getQuestionId());
        List<CommentsDTO> comments = answer.getComments();
        assertThat(comments, hasSize(1));
        CommentsDTO comment = comments.get(0);
        assertEquals(content, comment.getContents());
        assertEquals(LocalDate.now(), comment.getLocalDate());
        assertEquals(userForComment.getId(), comment.getUser().getId());
        assertEquals(userForComment.getDisplayName(), comment.getUser().getDisplayName());
    }

    @Test
    void shouldReturn400_WhenAnswerDoesNotExist(){
        // given
        User user = registeredUserCreator.registerNewUserWith("any");

        // when
        ResponseEntity<Void> response = sut.addComment(new AddCommentDTO(), 400L,500L, user);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

