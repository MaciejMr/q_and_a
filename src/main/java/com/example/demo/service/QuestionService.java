package com.example.demo.service;

import com.example.demo.dto.AddQuestionDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.QuestionHistoryDTO;
import com.example.demo.dto.QuestionWithAnswersAndCommentsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    Long addQuestion(AddQuestionDTO addQuestionDTO, UserDetails userDetails);

    Page<QuestionDTO> getFor(String category, Pageable pageable);

    Optional<QuestionWithAnswersAndCommentsDTO> getById(Long questionId);

    List<QuestionHistoryDTO> getAllForUser(UserDetails userDetails);
}
