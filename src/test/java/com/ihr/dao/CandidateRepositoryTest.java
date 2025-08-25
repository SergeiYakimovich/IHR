package com.ihr.dao;

import com.ihr.factory.CandidateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CandidateRepositoryTest {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private SkillRepository skillRepository;
    @BeforeEach
    void setUp() {
        candidateRepository.deleteAll();
    }

    @Test
    void testRepository() {
        saveCandidateWithSkills(CandidateFactory.getCandidateEntity());
        List<CandidateEntity> all = candidateRepository.findAll();

        assertEquals(1, all.size());
        assertEquals("Ivan", all.get(0).getName());
        assertEquals("New York", all.get(0).getAddress());
        assertEquals(2, all.get(0).getSkills().size());
        assertEquals(1, all.get(0).getSkillLevel("Java"));
    }

    @Test
    void updateAddress() {
        CandidateEntity savedEntity = saveCandidateWithSkills(CandidateFactory.getCandidateEntity());
        int result = candidateRepository.updateAddress(savedEntity.getId(), "London");
        CandidateEntity updatedEntity = candidateRepository.findAll().get(0);

        assertEquals(1, result);
        assertEquals("London", updatedEntity.getAddress());
    }

    @Test
    void findByName() {
        CandidateEntity savedEntity = saveCandidateWithSkills(CandidateFactory.getCandidateEntity());
        CandidateEntity foundEntity = candidateRepository.findByName("va").get(0);

        assertThat(savedEntity).usingRecursiveComparison().isEqualTo(foundEntity);
    }

    private CandidateEntity saveCandidateWithSkills(CandidateEntity candidateEntity) {
        skillRepository.saveAll(candidateEntity.getSkills().keySet());
        return candidateRepository.save(candidateEntity);
    }
}