package com.ihr.service;

import com.ihr.dto.CandidateDto;
import com.ihr.dto.VacancyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CandidateMatcher {
    public List<CandidateDto> findTopCandidates(VacancyDto vacancyDto, List<CandidateDto> candidates, Integer limit) {
        if (vacancyDto == null || vacancyDto.getSkills() == null || candidates == null) {
            return Collections.emptyList();
        }

        Map<String, Integer> vacancySkills = vacancyDto.getSkills();

        // Вычисляем score для каждого кандидата
        List<CandidateScore> candidateScores = candidates.stream()
                .filter(candidate -> candidate != null && candidate.getSkills() != null)
                .map(candidate -> {
                    double score = calculateDotProduct(candidate.getSkills(), vacancySkills);
                    return new CandidateScore(candidate, score);
                })
                .sorted(Comparator.comparingDouble(CandidateScore::getScore).reversed())
                .limit(limit)
                .toList();

        return candidateScores.stream()
                .map(CandidateScore::getCandidate)
                .collect(Collectors.toList());
    }

    private double calculateDotProduct(Map<String, Integer> candidateSkills, Map<String, Integer> vacancySkills) {
        double score = 0.0;

        // Проходим по всем навыкам вакансии
        for (Map.Entry<String, Integer> vacancySkill : vacancySkills.entrySet()) {
            String skillName = vacancySkill.getKey();
            Integer vacancySkillLevel = vacancySkill.getValue();

            // Если у кандидата есть этот навык, умножаем уровни
            if (candidateSkills.containsKey(skillName)) {
                Integer candidateSkillLevel = candidateSkills.get(skillName);
                score += candidateSkillLevel * vacancySkillLevel;
            }
            // Если навыка нет у кандидата, не добавляем ничего (эквивалентно умножению на 0)
        }

        return score;
    }

    // Вспомогательный класс для хранения кандидата и его score
    @AllArgsConstructor
    @Getter
    private static class CandidateScore {
        private final CandidateDto candidate;
        private final double score;
    }

}
