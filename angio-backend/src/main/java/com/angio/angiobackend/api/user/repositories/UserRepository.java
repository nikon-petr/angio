package com.angio.angiobackend.api.user.repositories;

import com.angio.angiobackend.api.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    @Query("select u from User u left join fetch u.roles r left join fetch u.ownedRolesToManage left join fetch u.organization left join fetch r.permissions where u.id = :id")
    Optional<User> findById(@Param("id") UUID id);

    @Query("select u from User u left join fetch u.roles r left join fetch u.ownedRolesToManage left join fetch r.permissions where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    List<User> findByEmailIn(Collection<String> email);
}