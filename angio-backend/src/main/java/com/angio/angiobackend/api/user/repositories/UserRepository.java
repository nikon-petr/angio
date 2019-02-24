package com.angio.angiobackend.api.user.repositories;

import com.angio.angiobackend.api.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from User u left join fetch u.roles r left join fetch r.permissions where u.id = :id")
    Optional<User> findById(@Param("id") UUID id);

    @Query("select u from User u left join fetch u.roles r left join fetch r.permissions where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}