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
    private CandidateRepository userRepository;
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRepository() {
        userRepository.save(CandidateFactory.getUserEntity());
        List<CandidateEntity> all = userRepository.findAll();

        assertEquals(1, all.size());
        assertEquals("Ivan", all.get(0).getName());
        assertEquals("New York", all.get(0).getAddress());
    }

    @Test
    void updateAddress() {
        CandidateEntity savedEntity = userRepository.save(CandidateFactory.getUserEntity());
        int result = userRepository.updateAddress(savedEntity.getId(), "London");
        CandidateEntity updatedEntity = userRepository.findAll().get(0);

        assertEquals(1, result);
        assertEquals("London", updatedEntity.getAddress());
    }

    @Test
    void findByName() {
        CandidateEntity savedEntity = userRepository.save(CandidateFactory.getUserEntity());
        CandidateEntity foundEntity = userRepository.findByName("va").get(0);

        assertThat(savedEntity).usingRecursiveComparison().isEqualTo(foundEntity);
    }
}