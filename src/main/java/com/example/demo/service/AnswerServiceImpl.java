package com.example.demo.service;

import com.example.demo.dto.AddAnswerDTO;
import com.example.demo.dto.AnswerHistoryDTO;
import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;


    @Autowired
    public AnswerServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public boolean addAnswer(AddAnswerDTO addAnswerDTO, Long questionId, UserDetails userDetails) {
        User user = (User)userDetails;
        Optional<Question> questionOp = questionRepository.findById(questionId);
        if (questionOp.isPresent()) {
            Answer answer = new Answer(user, addAnswerDTO.getContent());
            Question question = questionOp.get();
            question.addAnswer(answer);
            questionRepository.save(question);
            return true;
        }
        return false;
    }

    public boolean addPositiveVote(Long answerId, UserDetails userDetails) {
        User user = (User)userDetails;
        Optional<Answer> answerOp = answerRepository.findById(answerId);
        if (answerOp.isPresent()) {
            Answer answer = answerOp.get();
            answer.uprate(user.getId());
            answerRepository.save(answer);
            return true;
        }
        return false;
    }

    public boolean addNegativeVote(Long answerId, UserDetails userDetails) {
        User user = (User)userDetails;
        Optional<Answer> answerOp = answerRepository.findById(answerId);
        if (answerOp.isPresent()) {
            Answer answer = answerOp.get();
            answer.downrate(user.getId());
            answerRepository.save(answer);
            return true;
        }
        return false;
    }

    public List<AnswerHistoryDTO> getAllAnswersForUser(UserDetails userDetails) {
        User user = (User)userDetails;
        return answerRepository.findAnswersForUser(user.getId());
    }
}
