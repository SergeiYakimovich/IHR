package com.ihr.service;

import com.ihr.dto.CandidateDto;

import java.util.List;

public interface CandidateService {
    CandidateDto save(CandidateDto candidateDto);
    List<CandidateDto> findByName(String name);
    List<CandidateDto> findAll();
    boolean updateAddress(Long id, String newAddress);
}
