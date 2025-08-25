package com.ihr.service;

import com.ihr.dao.CandidateEntity;
import com.ihr.dao.CandidateRepository;
import com.ihr.dto.CandidateDto;
import com.ihr.mapper.CandidateMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    public static final int SUCCESS_UPDATE_RESULT = 1;
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Override
    @Nonnull
    public CandidateDto save(@Nonnull CandidateDto candidateDto) {
        log.info("Saving candidate: {}", candidateDto);
        CandidateEntity candidateEntity = candidateMapper.toEntity(candidateDto);
        CandidateDto savedCandidate = Optional.of(candidateRepository.save(candidateEntity))
                .map(candidateMapper::toDto)
                .orElse(null);
        log.info("Saved candidate: {}", savedCandidate);
        return savedCandidate;
    }

    @Override
    @Nonnull
    public List<CandidateDto> findByName(@Nonnull String name) {
        log.info("Finding candidates by name: {}", name);
        List<CandidateEntity> candidateEntityList = candidateRepository.findByName(name);
        List<CandidateDto> candidateDtoList = candidateMapper.toDtoList(candidateEntityList);
        log.info("Found candidates: {}", candidateDtoList);
        return candidateDtoList;
    }

    @Override
    public List<CandidateDto> findAll() {
        return candidateRepository.findAll().stream()
                .map(candidateMapper::toDto)
                .toList();
    }

    @Override
    public boolean updateAddress(@Nonnull Long id, @Nonnull String newAddress) {
        log.info("Updating address for candidate with id: {}, new address: {}", id, newAddress);
        int result = candidateRepository.updateAddress(id, newAddress);
        log.info("Updated candidate: {}", result);
        return SUCCESS_UPDATE_RESULT == result;
    }
}
