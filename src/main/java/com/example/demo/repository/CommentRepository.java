package com.example.demo.repository;

import com.example.demo.dto.CommentHistoryDTO;
import com.example.demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = Queries.GET_COMMENTS_FOR_USER, nativeQuery = true)
    List<CommentHistoryDTO> findCommentsForUser(Long id);
}
