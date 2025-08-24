package com.ihr.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "vacancies")
@Getter
@Setter
@NoArgsConstructor
public class VacancyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "employer", nullable = false)
    private String employer;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyJoinColumn(name = "skill_id") // Ключом мапы будет SkillEntity
    private Map<SkillEntity, VacancySkillEntity> skills = new HashMap<>();

    // Вспомогательный метод для получения уровня по названию навыка
    public Integer getSkillLevel(String skillName) {
        return skills.entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals(skillName))
                .findFirst()
                .map(entry -> entry.getValue().getLevel())
                .orElse(null);
    }

    // Вспомогательный метод для добавления навыка
    public void addSkill(SkillEntity skill, Integer level) {
        VacancySkillEntity vacancySkill = new VacancySkillEntity();
        vacancySkill.setVacancy(this);
        vacancySkill.setSkill(skill);
        vacancySkill.setLevel(level);
        skills.put(skill, vacancySkill);
    }

    // Вспомогательный метод для получения навыков в формате DTO
    public Map<String, Integer> getSkillsForDto() {
        return skills.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getName(),
                        entry -> entry.getValue().getLevel()
                ));
    }

}
