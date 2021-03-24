package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private String contents;
    private LocalDate localDate;
    private Long rating;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Vote> votes;

    @Autowired
    public Answer(User user, String contents) {
        this.user = user;
        this.contents = contents;
        this.localDate = LocalDate.now();
        this.rating = 0L;
        this.comments = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    protected Answer() {
    }

    public Long getRating() {
        return rating;
    }

    public String getContents() {
        return contents;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void uprate(Long userId) {
        Optional<Vote> previousVote = votes.stream().filter(vote -> vote.getUserId().equals(userId)).findFirst();
        previousVote.ifPresentOrElse(
                vote -> {
                    if (vote.getVoteValue() == VoteValue.POSITIVE) {
                        // do nothing
                    } else {
                        Long newRating = rating + 2;
                        rating = newRating;
                        vote.setVoteValue(VoteValue.POSITIVE);
                    }
                },
                () -> {
                    Long newRating = rating + 1;
                    rating = newRating;
                    Vote vote = new Vote(userId, VoteValue.POSITIVE);
                    votes.add(vote);

                });
    }

    public void downrate(Long userId) {
        Optional<Vote> previousVote = votes.stream().filter(vote -> vote.getUserId().equals(userId)).findFirst();
        previousVote.ifPresentOrElse(
                vote -> {
                    if (vote.getVoteValue() == VoteValue.NEGATIVE) {
                        // do nothing
                    } else {
                        Long newRating = rating - 2;
                        rating = newRating;
                        vote.setVoteValue(VoteValue.NEGATIVE);
                    }
                },
                () -> {
                    Long newRating = rating - 1;
                    rating = newRating;
                    Vote vote = new Vote(userId, VoteValue.NEGATIVE);
                    votes.add(vote);

                });

    }

}