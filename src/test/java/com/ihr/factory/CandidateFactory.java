package com.ihr.factory;

import com.ihr.dao.CandidateEntity;
import com.ihr.dao.CandidateSkillEntity;
import com.ihr.dao.SkillEntity;
import com.ihr.dto.CandidateDto;

import java.util.Map;

public class CandidateFactory {
    public static CandidateDto getCandidateDto() {
        return CandidateDto.builder()
                .name("Ivan")
                .address("New York")
                .skills(Map.of("Java", 1, "Gradle", 2))
                .build();
    }
    public static CandidateEntity getCandidateEntity() {
        SkillEntity skillEntityJava = new SkillEntity();
        skillEntityJava.setName("Java");
        CandidateSkillEntity candidateSkillEntityJava = new CandidateSkillEntity();
        candidateSkillEntityJava.setSkill(skillEntityJava);
        candidateSkillEntityJava.setLevel(1);

        SkillEntity skillEntityGradle = new SkillEntity();
        skillEntityGradle.setName("Gradle");
        CandidateSkillEntity candidateSkillEntityGradle = new CandidateSkillEntity();
        candidateSkillEntityGradle.setSkill(skillEntityGradle);
        candidateSkillEntityGradle.setLevel(2);

        Map<SkillEntity, CandidateSkillEntity> skills = Map.of(skillEntityJava, candidateSkillEntityJava, skillEntityGradle, candidateSkillEntityGradle);

        CandidateEntity candidateEntity = new CandidateEntity();
        candidateEntity.setName("Ivan");
        candidateEntity.setAddress("New York");
        candidateEntity.setSkills(skills);

        candidateSkillEntityJava.setCandidate(candidateEntity);
        candidateSkillEntityGradle.setCandidate(candidateEntity);

        return candidateEntity;
    }
}
