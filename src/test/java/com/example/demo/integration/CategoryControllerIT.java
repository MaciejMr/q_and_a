package com.example.demo.integration;

import com.example.demo.controller.CategoryController;
import com.example.demo.controller.QuestionController;
import com.example.demo.dto.AddQuestionDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.integration.util.QuestionUtil;
import com.example.demo.integration.util.RegisteredUserUtil;
import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CategoryControllerIT {

    @Autowired
    private CategoryController sut;
    @Autowired
    private RegisteredUserUtil registeredUserCreator;
    @Autowired
    private QuestionUtil questionUtil;
    @Autowired
    private QuestionController questionController;

    @Test
    void shouldReturnAllUniqueCategories_WhenThereAreQuestionsWithDifferentCategories() {
        // given
        String category1 = "cat1";
        String category2 = "cat2";
        String category2b = "CAT2";
        String category3 = "cat3";
        String category4 = "cat4";
        User user = registeredUserCreator.registerNewUserWith("username");
        questionUtil.addNewQuestion(user, category1);
        questionUtil.addNewQuestion(user, category1);
        questionUtil.addNewQuestion(user, category2);
        questionUtil.addNewQuestion(user, category2b);
        questionUtil.addNewQuestion(user, category3);
        questionUtil.addNewQuestion(user, category4);
        questionUtil.addNewQuestion(user, category4);
        questionUtil.addNewQuestion(user, category4);

        // when
        ResponseEntity<List<String>> result = sut.getCategories();

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        List<String> categories = result.getBody();
        assertThat(categories, hasSize(4));
        assertThat(categories, containsInAnyOrder(category1, category2, category3, category4));
    }

    @Test
    void shouldReturnAllQuestionsForGivenCategory_WhenThereAreQuestionsForCategory() {
        // given
        String category1 = "cat1";
        String category2 = "cat2";
        User user = registeredUserCreator.registerNewUserWith("username");
        questionUtil.addNewQuestion(user, category1);
        questionUtil.addNewQuestion(user, category1);
        questionUtil.addNewQuestion(user, category2);

        // when
        ResponseEntity<Page<QuestionDTO>> result = sut.getQuestionsFor(category1, PageRequest.of(0, 10));

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        List<QuestionDTO> questions = result.getBody().get().collect(Collectors.toList());
        assertThat(questions, hasSize(2));
    }

    @Test
    void shouldReturnCorrectQuestionsDetails_WhenThereIsQuestionForCategory() {
        // given
        String category = "cat1";
        String content = "content";
        User user = registeredUserCreator.registerNewUserWith("username");

        AddQuestionDTO addQuestionDTO = new AddQuestionDTO();
        addQuestionDTO.setCategory(category);
        addQuestionDTO.setContent(content);
        Long qId = questionController.addQuestion(addQuestionDTO, user).getBody();

        // when
        ResponseEntity<Page<QuestionDTO>> result = sut.getQuestionsFor(category, PageRequest.of(0, 10));

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        QuestionDTO questionDTO = result.getBody().get().findFirst().get();
        assertEquals(qId, questionDTO.getId());
        assertEquals(category, questionDTO.getCategory());
        assertEquals(content, questionDTO.getContents());
        assertEquals(LocalDate.now(), questionDTO.getLocalDate());
        assertEquals(user.getId(), questionDTO.getUser().getId());
        assertEquals(user.getDisplayName(), questionDTO.getUser().getDisplayName());
    }
}