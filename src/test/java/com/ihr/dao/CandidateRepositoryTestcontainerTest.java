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
    private CandidateRepository userRepository;

    @Test
    void testRepository() {
        userRepository.save(CandidateFactory.getUserEntity());
        List<CandidateEntity> all = userRepository.findAll();

        assertEquals(1, all.size());
        assertEquals("Ivan", all.get(0).getName());
        assertEquals("New York", all.get(0).getAddress());
    }
}
