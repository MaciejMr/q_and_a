package com.example.demo.dto;

import java.time.LocalDate;

public interface QuestionHistoryDTO {

    Long getId();

    String getContents();

    LocalDate getLocalDate();

    String getCategory();
}
