package com.example.demo.controller;

import com.example.demo.dto.AnswerHistoryDTO;
import com.example.demo.dto.CommentHistoryDTO;
import com.example.demo.dto.QuestionHistoryDTO;
import com.example.demo.service.AnswerService;
import com.example.demo.service.CommentService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private QuestionService questionService;
    private AnswerService answerService;
    private CommentService commentService;

    @Autowired
    public HistoryController(QuestionService questionService, AnswerService answerService, CommentService commentService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.commentService = commentService;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionHistoryDTO>> questionsHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<QuestionHistoryDTO> q = questionService.getAllForUser(userDetails);
        return new ResponseEntity<>(q, HttpStatus.OK);
    }

    @GetMapping("/answers")
    public ResponseEntity<List<AnswerHistoryDTO>> answersHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<AnswerHistoryDTO> a = answerService.getAllAnswersForUser(userDetails);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentHistoryDTO>> commentsHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<CommentHistoryDTO> c = commentService.getAllCommentsForUser(userDetails);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
}

