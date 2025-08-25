package com.ihr.dao;

import com.ihr.factory.CandidateFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CandidateRepositoryTestcontainerTest extends TestcontainerInitializer {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private SkillRepository skillRepository;
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

    private CandidateEntity saveCandidateWithSkills(CandidateEntity candidateEntity) {
        skillRepository.saveAll(candidateEntity.getSkills().keySet());
        return candidateRepository.save(candidateEntity);
    }
}
