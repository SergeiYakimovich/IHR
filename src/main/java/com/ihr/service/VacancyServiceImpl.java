package com.ihr.service;

import com.ihr.dao.CandidateRepository;
import com.ihr.dao.VacancyEntity;
import com.ihr.dao.VacancyRepository;
import com.ihr.dto.CandidateDto;
import com.ihr.dto.VacancyDto;
import com.ihr.mapper.VacancyMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final CandidateRepository candidateRepository;
    private final VacancyMapper vacancyMapper;

    @Override
    @Nonnull
    public VacancyDto save(@Nonnull VacancyDto vacancyDto) {
        log.info("Saving vacancy: {}", vacancyDto);
        VacancyEntity vacancyEntity = vacancyMapper.toEntity(vacancyDto);
        VacancyDto savedVacancy = Optional.of(vacancyRepository.save(vacancyEntity))
                .map(vacancyMapper::toDto)
                .orElse(null);
        log.info("Saved vacancy: {}", savedVacancy);
        return savedVacancy;
    }

    @Override
    @Nonnull
    public List<VacancyDto> findByName(@Nonnull String name) {
        log.info("Finding vacancies by name: {}", name);
        List<VacancyEntity> vacancyEntityList = vacancyRepository.findByName(name);
        List<VacancyDto> vacancyDtoList = vacancyMapper.toDtoList(vacancyEntityList);
        log.info("Found vacancies: {}", vacancyDtoList);
        return vacancyDtoList;
    }
    @Override
    public List<CandidateDto> findBests(VacancyDto vacancyDto) {

        return null;
    }

}
