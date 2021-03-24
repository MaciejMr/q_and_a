package com.example.demo.repository;

public final class Queries {

    private Queries(){}

    public static final String GET_ANSWERS_FOR_USER = "SELECT a.id as answerId, a.local_date as localDate, a.rating as rating, a.contents as contents, q.id as questionId " +
            " FROM answer as a\n" +
            "join question_answers as qa\n" +
            "on a.id = qa.answers_id\n" +
            "join question as q \n" +
            "on q.id = qa.question_id where a.user_id = ?1\n" +
            "order by a.id desc";

    public static final String GET_COMMENTS_FOR_USER = "SELECT c.id as commentId, c.local_date as localDate, c.contents as contents, q.id as questionId \n" +
            " FROM comment as c \n" +
            "join answer_comments as ac \n" +
            "on c.id = ac.comments_id \n" +
            "join answer as a \n" +
            "on a.id = ac.answer_id \n" +
            "join question_answers as qa \n" +
            "on qa.answers_id = a.id \n" +
            "join question as q \n" +
            "on q.id = qa.question_id \n" +
            "where c.user_id = ?1 \n" +
            "order by c.id desc";
}

