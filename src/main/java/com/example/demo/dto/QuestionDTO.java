package com.example.demo.dto;

import java.time.LocalDate;

public interface QuestionDTO {

    Long getId();

    String getContents();

    LocalDate getLocalDate();

    String getCategory();

    UserDTO getUser();
}

