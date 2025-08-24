package com.ihr.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Навык вакансии
@Entity
@DiscriminatorValue("VACANCY")
@Getter
@Setter
public class VacancySkillEntity extends BaseSkillEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id")
    private VacancyEntity vacancy;
}
