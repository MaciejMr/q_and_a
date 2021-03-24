package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

public interface AnswersWithCommentsDTO {

    Long getId();

    UserDTO getUser();

    String getContents();

    LocalDate getLocalDate();

    Long getRating();

    List<CommentsDTO> getComments();
}
