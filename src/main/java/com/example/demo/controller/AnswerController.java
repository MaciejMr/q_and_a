package com.example.demo.controller;

import com.example.demo.dto.AddAnswerDTO;
import com.example.demo.service.AnswerService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class AnswerController {

    private AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/{questionId}/answers")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {@ApiResponse(code = 400, message = "No question with such Id"),
            @ApiResponse(code = 201, message = "Created")})
    public ResponseEntity<?> addAnswer(@RequestBody AddAnswerDTO addAnswerDTO,
                                       @PathVariable Long questionId,
                                       @AuthenticationPrincipal UserDetails userDetails) {

        boolean success = answerService.addAnswer(addAnswerDTO, questionId, userDetails);
        if (success) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body("No question with such Id");
    }

    @PostMapping("/{questionId}/answers/{answerId}/votes/positives")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {@ApiResponse(code = 400, message = "No answer with such Id"),
            @ApiResponse(code = 201, message = "Created")})
    public ResponseEntity<Void> addPositiveVote(
            @PathVariable String questionId,
            @PathVariable Long answerId,
            @AuthenticationPrincipal UserDetails userDetails) {

        boolean success = answerService.addPositiveVote(answerId, userDetails);
        if (success) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity("No answer with such Id", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{questionId}/answers/{answerId}/votes/negatives")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {@ApiResponse(code = 400, message = "No answer with such Id"),
            @ApiResponse(code = 201, message = "Created")})
    public ResponseEntity<Void> addNegativeVote(
            @PathVariable String questionId,
            @PathVariable Long answerId,
            @AuthenticationPrincipal UserDetails userDetails) {

        boolean success = answerService.addNegativeVote(answerId, userDetails);
        if (success) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity("No answer with such Id", HttpStatus.BAD_REQUEST);
    }
}