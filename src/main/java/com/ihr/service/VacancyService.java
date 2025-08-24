package com.ihr.service;

import com.ihr.dto.CandidateDto;
import com.ihr.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    VacancyDto save(VacancyDto vacancyDto);
    List<VacancyDto> findByName(String name);
    List<CandidateDto> findBests(VacancyDto vacancyDto);
}
