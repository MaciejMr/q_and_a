package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Vote {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private VoteValue voteValue;

    public Vote(Long userId, VoteValue voteValue) {
        this.userId = userId;
        this.voteValue = voteValue;
    }

    protected Vote() {
    }

    public Long getUserId() {
        return userId;
    }

    public VoteValue getVoteValue() {
        return voteValue;
    }

    public void setVoteValue(VoteValue voteValue) {
        this.voteValue = voteValue;
    }
}
