package com.angio.server.analyse.repositories;

import com.angio.server.analyse.entities.AnalyseInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface AnalyseInfoCrudRepository extends JpaRepository<AnalyseInfoEntity, Long> {

    Page<AnalyseInfoEntity> findAll(Specification<AnalyseInfoEntity> spec, Pageable pegeable);

    @Query("SELECT a from AnalyseInfoEntity a " +
            "  JOIN a.patient p " +
            "  JOIN a.user u " +
            "  JOIN u.userInfo ui " +
            "WHERE (LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(a.shortDescription) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(a.analyseType) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "      OR LOWER(p.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.fullName.patronymic) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.policy) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(ui.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(ui.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<AnalyseInfoEntity> findAll(@Param("search") String search, Pageable pageable);

    @Query("SELECT a from AnalyseInfoEntity a " +
            "  JOIN a.patient p " +
            "  JOIN a.user u " +
            "  JOIN u.userInfo ui " +
            "WHERE (LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(a.shortDescription) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(a.analyseType) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "      OR LOWER(p.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.fullName.patronymic) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.policy) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(ui.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(ui.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')))" +
            "      AND (DATE(a.analyseDate) = DATE(:date))")
    Page<AnalyseInfoEntity> findAll(@Param("search") String search, @Param("date") String date, Pageable pageable);

    @Query("SELECT COUNT(a) from AnalyseInfoEntity a " +
            "  JOIN a.patient p " +
            "  JOIN a.user u " +
            "  JOIN u.userInfo ui " +
            "WHERE (LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(a.shortDescription) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(a.analyseType) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "      OR LOWER(p.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.fullName.patronymic) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.policy) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(ui.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(ui.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')))")
    long count(@Param("search") String search);

    @Query("SELECT COUNT(a) from AnalyseInfoEntity a " +
            "  JOIN a.patient p " +
            "  JOIN a.user u " +
            "  JOIN u.userInfo ui " +
            "WHERE (LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(a.shortDescription) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(a.analyseType) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "      OR LOWER(p.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.fullName.patronymic) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(p.policy) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(ui.fullName.firstname) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "      OR LOWER(ui.fullName.lastname) LIKE LOWER(CONCAT('%', :search, '%')))" +
            "      AND (DATE(a.analyseDate) = DATE(:date))")
    long count(@Param("search") String search, @Param("date") String date);
}