package com.ihr.mapper;

import com.ihr.dao.CandidateEntity;
import com.ihr.dao.CandidateSkillEntity;
import com.ihr.dao.SkillEntity;
import com.ihr.dao.SkillRepository;
import com.ihr.dto.CandidateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CandidateMapper {
    @Autowired
    private SkillRepository skillRepository;

    public CandidateEntity toEntity(CandidateDto dto) {
        CandidateEntity entity = new CandidateEntity();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());

        if (dto.getSkills() != null) {
            updateSkills(entity, dto.getSkills());
        }

        return entity;
    }

    public void updateSkills(CandidateEntity entity, Map<String, Integer> newSkills) {
        // Очищаем существующие навыки
        entity.getSkills().clear();

        // Добавляем новые навыки
        for (Map.Entry<String, Integer> entry : newSkills.entrySet()) {
            String skillName = entry.getKey();
            Integer level = entry.getValue();

            SkillEntity skillEntity = skillRepository.findByName(skillName)
                    .orElseGet(() -> {
                        // Создаем новый навык, если не найден
                        SkillEntity newSkill = new SkillEntity();
                        newSkill.setName(skillName);
                        return skillRepository.save(newSkill);
                    });

            CandidateSkillEntity candidateSkill = new CandidateSkillEntity();
            candidateSkill.setCandidate(entity);
            candidateSkill.setSkill(skillEntity);
            candidateSkill.setLevel(level);

            // Ключом теперь является SkillEntity, а не String
            entity.getSkills().put(skillEntity, candidateSkill);
        }
    }

    public CandidateDto toDto(CandidateEntity entity) {
        CandidateDto dto = new CandidateDto();
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());

        // Преобразуем Map<SkillEntity, CandidateSkillEntity> в Map<String, Integer>
        Map<String, Integer> skillsMap = entity.getSkills().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getName(), // Извлекаем имя из SkillEntity
                        entry -> entry.getValue().getLevel() // Извлекаем уровень из CandidateSkillEntity
                ));

        dto.setSkills(skillsMap);
        return dto;
    }

    public List<CandidateDto> toDtoList(List<CandidateEntity> candidateEntities) {
        return candidateEntities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<CandidateEntity> toEntityList(List<CandidateDto> candidateDtos) {
        return candidateDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
