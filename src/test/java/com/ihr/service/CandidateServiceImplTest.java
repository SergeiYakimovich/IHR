package com.ihr.service;

import com.ihr.dao.CandidateRepository;
import com.ihr.dto.CandidateDto;
import com.ihr.factory.CandidateFactory;
import com.ihr.mapper.CandidateMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandidateServiceImplTest {
    @Mock
    private CandidateMapper candidateMapper;
    @Mock
    private CandidateRepository candidateRepository;
    @InjectMocks
    private CandidateServiceImpl candidateService;

    @Test
    void save() {
        // given
        CandidateDto candidateDto = CandidateFactory.getCandidateDto();
        when(candidateRepository.save(any())).thenReturn(CandidateFactory.getCandidateEntity());
        when(candidateMapper.toEntity(any())).thenReturn(CandidateFactory.getCandidateEntity());
        when(candidateMapper.toDto(any())).thenReturn(candidateDto);

        // when
        CandidateDto result = candidateService.save(candidateDto);

        // then
        assertThat(candidateDto).usingRecursiveAssertion().isEqualTo(result);
    }
}