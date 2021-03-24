package com.example.demo.controller;

import com.example.demo.dto.AddCommentDTO;
import com.example.demo.service.CommentService;
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
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("{questionId}/answers/{answerId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {@ApiResponse(code = 400, message = "No comment with such Id"),
            @ApiResponse(code = 201, message = "Created")})
    public ResponseEntity<Void> addComment(
            @RequestBody AddCommentDTO addCommentDTO,
            @PathVariable Long questionId,
            @PathVariable Long answerId,
            @AuthenticationPrincipal UserDetails userDetails) {

        boolean success = commentService.addComment(addCommentDTO, answerId, userDetails);
        if (success) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity("No comment with such Id", HttpStatus.BAD_REQUEST);
    }
}