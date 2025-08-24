package com.ihr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update CandidateEntity e " +
            "set e.address = :newAddress " +
            "where e.id = :id ")
    int updateAddress(@Param("id") Long id, @Param("newAddress") String newAddress);

    @Query("select e from CandidateEntity e " +
            "where e.name like concat('%',:name,'%') ")
    List<CandidateEntity> findByName(@Param("name") String name);
}
