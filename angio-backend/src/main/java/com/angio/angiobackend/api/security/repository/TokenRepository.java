package com.angio.angiobackend.api.security.repository;

import com.angio.angiobackend.api.security.entity.Token;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID>, JpaSpecificationExecutor<Token> {
}
