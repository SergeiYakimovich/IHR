package com.ihr.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Навык пользователя
@Entity
@DiscriminatorValue("CANDIDATE")
@Getter
@Setter
public class CandidateSkillEntity extends BaseSkillEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidate;
}
