# Welcome to Q&A app

Questions and answers app created in Java

## Table of contents
* [Introduction](#introduction)
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Functionality](#functionality)
* [Future](#future)

## Introduction
Questions and answers is a final project created as a summary of Java Bootcamp runned by Software Development Academy in London. The aim was to create an app similar to q&a forum like stackoverflow.com using technologies learned during the course as well as some new implementations. Project was written in InteliJ IDEA Ultimate and deployed on GitHub directly from software.

## General info
The app allows user to register and login. Authenticated user can add questions, answers and comments. Moreover there is possibility for users to upvote and downvote questions and responses. Implemented in app is also history, where user can see all queries. Questions are divided into categories for easy browsing.

## Technologies
- Java 11
- SpringBoot 2.3.3
- SpringSecurity 5.2.2
- JUnit 5
- Hibernate 5.4.20
- JJWT 0.9.1
- Swagger2 2.9.2
- Hamcrest 1.3

Database:
- MySQL
- H2 (Tests only)

## Setup
To run this project download it locally and open using for example InteliJ IDEA. Run QuestionsApplication and head to browser. Type http://localhost:8082/swagger-ui.html and you are able to see swagger API documentation.

## Functionality

Functionalities listed below. Instruction how to operate in swagger in parentheses.

- Register new users with encrypted passwords (User-controller -> Try it out)
- Login (token-controller)
- Add question and view question by id (question-controller)
- Add answer and vote (answer-controller)
- Add comment (comment-controller)
- View all categories and view questions for given category (category-controller)
- View questions, answers and comments history(history-controller)

## Future
Although project was completed based on fixed time frame there are plans for future front-end implementation.
