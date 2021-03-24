package com.example.demo.dto;

import java.time.LocalDate;

public interface AnswerHistoryDTO {

    Long getAnswerId();

    String getContents();

    LocalDate getLocalDate();

    Long getRating();

    Long getQuestionId();
}
