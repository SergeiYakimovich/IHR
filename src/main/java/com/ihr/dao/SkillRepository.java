package com.ihr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface SkillRepository extends JpaRepository<SkillEntity, Long> {
    Optional<SkillEntity> findByName(String name);
}
