package com.angio.server.security.repositories;


import com.angio.server.security.entities.UserEntity;
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
            "      OR LOWER(CONCAT(ui.firstname, ' ', ui.lastname)) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "      OR LOWER(CONCAT(ui.lastname, ' ', ui.firstname)) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<UserEntity> findByQuery(@Param("query") String query, Pageable pageable);
}