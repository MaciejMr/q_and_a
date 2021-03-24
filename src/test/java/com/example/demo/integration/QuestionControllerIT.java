package com.example.demo.integration;

import com.example.demo.controller.QuestionController;
import com.example.demo.dto.AddQuestionDTO;
import com.example.demo.dto.QuestionWithAnswersAndCommentsDTO;
import com.example.demo.dto.UserDTO;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class QuestionControllerIT {

    @Autowired
    private QuestionController sut;
    @Autowired
    private RegisteredUserUtil registeredUserCreator;

    @Test
    void shouldAddNewQuestion_WhenAllInformationCorrect(){
        // given
        User userDetails = registeredUserCreator.registerNewUserWith("username");

        String category = "cat";
        String content = "content";

        AddQuestionDTO request = new AddQuestionDTO();
        request.setCategory(category);
        request.setContent(content);

        // when
        ResponseEntity<Long> result = sut.addQuestion(request, userDetails);

        // then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        ResponseEntity<QuestionWithAnswersAndCommentsDTO> questionResponse = sut.fullQuestion(result.getBody());
        assertEquals(HttpStatus.OK, questionResponse.getStatusCode());
        QuestionWithAnswersAndCommentsDTO question = questionResponse.getBody();
        assertEquals(category, question.getCategory());
        assertEquals(content, question.getContents());
        assertEquals(LocalDate.now(), question.getLocalDate());
        UserDTO user = question.getUser();
        assertEquals(userDetails.getId(), user.getId());
        assertEquals(userDetails.getDisplayName(), user.getDisplayName());
        assertThat(question.getAnswers(), IsEmptyCollection.empty());
    }

    @Test
    void shouldReturn404_WhenQuestionDoesNotExist(){
        // when
        ResponseEntity<QuestionWithAnswersAndCommentsDTO> questionResponse = sut.fullQuestion(500L);

        // then
        assertEquals(HttpStatus.NOT_FOUND, questionResponse.getStatusCode());
    }
}