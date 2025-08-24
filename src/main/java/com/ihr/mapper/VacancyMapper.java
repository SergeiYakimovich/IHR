package com.ihr.mapper;

import com.ihr.dao.SkillEntity;
import com.ihr.dao.SkillRepository;
import com.ihr.dao.VacancyEntity;
import com.ihr.dao.VacancySkillEntity;
import com.ihr.dto.VacancyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class VacancyMapper {
    @Autowired
    private SkillRepository skillRepository;

    public VacancyEntity toEntity(VacancyDto dto) {
        VacancyEntity entity = new VacancyEntity();
        entity.setEmployer(dto.getEmployer());

        if (dto.getSkills() != null) {
            updateSkills(entity, dto.getSkills());
        }

        return entity;
    }

    public void updateSkills(VacancyEntity entity, Map<String, Integer> newSkills) {
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

            VacancySkillEntity vacancySkill = new VacancySkillEntity();
            vacancySkill.setVacancy(entity);
            vacancySkill.setSkill(skillEntity);
            vacancySkill.setLevel(level);

            // Ключом теперь является SkillEntity, а не String
            entity.getSkills().put(skillEntity, vacancySkill);
        }
    }

    public VacancyDto toDto(VacancyEntity entity) {
        VacancyDto dto = new VacancyDto();
        dto.setEmployer(entity.getEmployer());

        // Преобразуем Map<SkillEntity, VacancySkillEntity> в Map<String, Integer>
        Map<String, Integer> skillsMap = entity.getSkills().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getName(), // Извлекаем имя из SkillEntity
                        entry -> entry.getValue().getLevel() // Извлекаем уровень из VacancySkillEntity
                ));

        dto.setSkills(skillsMap);
        return dto;
    }

    public List<VacancyDto> toDtoList(List<VacancyEntity> vacancyEntities) {
        return vacancyEntities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<VacancyEntity> toEntityList(List<VacancyDto> vacancyDtos) {
        return vacancyDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
