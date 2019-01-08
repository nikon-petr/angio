package com.angio.angiobackend.security.repositories;


import com.angio.angiobackend.security.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, String> {
    List<UserEntity> findByUsername(String username);
    List<UserEntity> findByEnabled(boolean enabled);

    @Query("SELECT u from UserEntity u " +
            "  JOIN u.userInfo ui " +
            "WHERE (LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "      OR LOWER(CONCAT(ui.fullName.firstname, ' ', ui.fullName.lastname)) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "      OR LOWER(CONCAT(ui.fullName.lastname, ' ', ui.fullName.firstname)) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<UserEntity> findByQuery(@Param("query") String query, Pageable pageable);
}