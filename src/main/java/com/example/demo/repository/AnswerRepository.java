package com.example.demo.repository;

import com.example.demo.dto.AnswerHistoryDTO;
import com.example.demo.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query(value = Queries.GET_ANSWERS_FOR_USER, nativeQuery = true)
    List<AnswerHistoryDTO> findAnswersForUser(Long userId);
}