package com.example.demo.service;

import com.example.demo.dto.AddCommentDTO;
import com.example.demo.dto.CommentHistoryDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CommentService {

    boolean addComment(AddCommentDTO addCommentDTO, Long answerId, UserDetails userDetails);

    List<CommentHistoryDTO> getAllCommentsForUser(UserDetails userDetails);

}

