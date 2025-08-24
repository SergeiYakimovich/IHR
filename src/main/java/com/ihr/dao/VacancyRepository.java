package com.ihr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface VacancyRepository extends JpaRepository<VacancyEntity, Long> {
    @Query("select e from VacancyEntity e " +
            "where e.employer like concat('%',:name,'%') ")
    List<VacancyEntity> findByName(@Param("name") String name);
}
