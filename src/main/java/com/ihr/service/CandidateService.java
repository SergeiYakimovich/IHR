package com.ihr.service;

import com.ihr.dto.CandidateDto;

import java.util.List;

public interface CandidateService {
    CandidateDto save(CandidateDto candidateDto);
    List<CandidateDto> findByName(String name);
    boolean updateAddress(Long id, String newAddress);
}
