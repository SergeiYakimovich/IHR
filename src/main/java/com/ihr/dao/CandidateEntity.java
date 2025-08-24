package com.ihr.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@NoArgsConstructor
public class CandidateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyJoinColumn(name = "skill_id")
    private Map<SkillEntity, CandidateSkillEntity> skills = new HashMap<>();

    // Вспомогательный метод
    public Integer getSkillLevel(String skillName) {
        return skills.entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals(skillName))
                .findFirst()
                .map(entry -> entry.getValue().getLevel())
                .orElse(null);
    }
}
