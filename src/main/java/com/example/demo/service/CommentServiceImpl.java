package com.example.demo.service;

import com.example.demo.dto.AddCommentDTO;
import com.example.demo.dto.CommentHistoryDTO;
import com.example.demo.model.Answer;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private AnswerRepository answerRepository;
    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(AnswerRepository answerRepository, CommentRepository commentRepository) {
        this.answerRepository = answerRepository;
        this.commentRepository = commentRepository;
    }

    public boolean addComment(AddCommentDTO addCommentDTO, Long answerId, UserDetails userDetails) {
        User user = (User)userDetails;
        Comment comment = new Comment(user, addCommentDTO.getContent());
        Optional<Answer> answerOp = answerRepository.findById(answerId);
        if (answerOp.isPresent()) {
            Answer answer = answerOp.get();
            answer.addComment(comment);
            answerRepository.save(answer);
            return true;
        }
        return false;
    }

    public List<CommentHistoryDTO> getAllCommentsForUser(UserDetails userDetails) {
        User user = (User)userDetails;
        return commentRepository.findCommentsForUser(user.getId());
    }
}
