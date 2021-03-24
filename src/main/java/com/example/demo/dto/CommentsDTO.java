package com.example.demo.dto;

import java.time.LocalDate;

public interface CommentsDTO {

    Long getId();

    UserDTO getUser();

    String getContents();

    LocalDate getLocalDate();
}


