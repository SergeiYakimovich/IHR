package com.ihr.factory;

import com.ihr.dao.CandidateEntity;
import com.ihr.dto.CandidateDto;

public class CandidateFactory {
    public static CandidateDto getUserDto() {
        return CandidateDto.builder().name("Ivan").address("New York").build();
    }
    public static CandidateEntity getUserEntity() {
        CandidateEntity candidateEntity = new CandidateEntity();
        candidateEntity.setName("Ivan");
        candidateEntity.setAddress("New York");
        return candidateEntity;
    }
}
