package com.example.demo.repository;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<QuestionDTO> findByCategory(String category, Pageable pageable);

    Optional<QuestionWithAnswersAndCommentsDTO> getOneById(Long questionId);

    List<QuestionHistoryDTO> findByUserOrderByIdDesc(User user);

}

