package com.example.demo.service;

import com.example.demo.dto.AddAnswerDTO;
import com.example.demo.dto.AnswerHistoryDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AnswerService {

    boolean addAnswer(AddAnswerDTO addAnswerDTO, Long questionId, UserDetails userDetails);

    boolean addPositiveVote(Long answerId, UserDetails userDetails);

    boolean addNegativeVote(Long answerId, UserDetails userDetails);

    List<AnswerHistoryDTO> getAllAnswersForUser(UserDetails userDetails);
}
