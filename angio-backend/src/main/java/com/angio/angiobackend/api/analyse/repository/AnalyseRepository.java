package com.angio.angiobackend.api.analyse.repository;

import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnalyseRepository extends JpaRepository<AnalyseEntity, Long> {

    Optional<AnalyseEntity> findById(Long id);
    Optional<AnalyseEntity> findOne(Specification<AnalyseEntity> specification);
    Page<AnalyseEntity> findAll(Specification<AnalyseEntity> spec, Pageable pegeable);

//    @Query("SELECT a from AnalyseInfoEntity a " +
//            "  JOIN a.patient p " +
//            "  JOIN a.user u " +
//            "  JOIN u.userInfo ui " +
//            "WHERE (LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(a.shortDescription) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(a.analyseType) LIKE LOWER(CONCAT('%', :search, '%'))" +
//            "      OR LOWER(p.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.fullName.patronymic) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.policy) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(ui.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(ui.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')))")
//    Page<AnalyseInfoEntity> findAll(@Param("search") String search, Pageable pageable);
//
//    @Query("SELECT a from AnalyseInfoEntity a " +
//            "  JOIN a.patient p " +
//            "  JOIN a.user u " +
//            "  JOIN u.userInfo ui " +
//            "WHERE (LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(a.shortDescription) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(a.analyseType) LIKE LOWER(CONCAT('%', :search, '%'))" +
//            "      OR LOWER(p.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.fullName.patronymic) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.policy) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(ui.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(ui.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')))" +
//            "      AND (DATE(a.analyseDate) = DATE(:date))")
//    Page<AnalyseInfoEntity> findAll(@Param("search") String search, @Param("date") String date, Pageable pageable);
//
//    @Query("SELECT COUNT(a) from AnalyseInfoEntity a " +
//            "  JOIN a.patient p " +
//            "  JOIN a.user u " +
//            "  JOIN u.userInfo ui " +
//            "WHERE (LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(a.shortDescription) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(a.analyseType) LIKE LOWER(CONCAT('%', :search, '%'))" +
//            "      OR LOWER(p.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.fullName.patronymic) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.policy) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(ui.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(ui.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')))")
//    long count(@Param("search") String search);
//
//    @Query("SELECT COUNT(a) from AnalyseInfoEntity a " +
//            "  JOIN a.patient p " +
//            "  JOIN a.user u " +
//            "  JOIN u.userInfo ui " +
//            "WHERE (LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(a.shortDescription) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(a.analyseType) LIKE LOWER(CONCAT('%', :search, '%'))" +
//            "      OR LOWER(p.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.fullName.patronymic) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(p.policy) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(ui.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "      OR LOWER(ui.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')))" +
//            "      AND (DATE(a.analyseDate) = DATE(:date))")
//    long count(@Param("search") String search, @Param("date") String date);
}