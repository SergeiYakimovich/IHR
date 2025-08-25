package com.ihr.service;

import com.ihr.dto.CandidateDto;
import com.ihr.dto.VacancyDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CandidateMatcherTest {
    @Test
    void findTopCandidates() {
        // Создаем вакансию
        var vacancy = new VacancyDto();
        vacancy.setEmployer("Tech Company");
        Map<String, Integer> vacancySkills = new HashMap<>();
        vacancySkills.put("Java", 3);
        vacancySkills.put("Spring", 2);
        vacancySkills.put("SQL", 1);
        vacancy.setSkills(vacancySkills);

        // Создаем кандидатов
        List<CandidateDto> candidates = new ArrayList<>();

        CandidateDto candidate1 = new CandidateDto();
        candidate1.setName("John");
        candidate1.setAddress("Address 1");
        Map<String, Integer> skills1 = new HashMap<>();
        skills1.put("SQL", 3);
        skills1.put("Spring", 2);
        skills1.put("Java", 1);
        candidate1.setSkills(skills1);

        CandidateDto candidate2 = new CandidateDto();
        candidate2.setName("Alice");
        candidate2.setAddress("Address 2");
        Map<String, Integer> skills2 = new HashMap<>();
        skills2.put("Python", 3); // Дополнительный навык
        skills2.put("Java", 2);
        skills2.put("Spring", 1);
        candidate2.setSkills(skills2);

        CandidateDto candidate3 = new CandidateDto();
        candidate3.setName("Bob");
        candidate3.setAddress("Address 3");
        Map<String, Integer> skills3 = new HashMap<>();
        skills3.put("Spring", 3);
        skills3.put("SQL", 2);
        skills3.put("Java", 1);

        candidate3.setSkills(skills3);

        candidates.add(candidate1);
        candidates.add(candidate2);
        candidates.add(candidate3);

        // Ищем лучших кандидатов
        CandidateMatcher matcher = new CandidateMatcher();
        List<CandidateDto> topCandidates = matcher.findTopCandidates(vacancy, candidates, 3);

        // Выводим результат
        System.out.println("Top 3 candidates:");
        for (int i = 0; i < topCandidates.size(); i++) {
            CandidateDto candidate = topCandidates.get(i);
            System.out.println((i + 1) + ". " + candidate.getName());
        }

    }
}