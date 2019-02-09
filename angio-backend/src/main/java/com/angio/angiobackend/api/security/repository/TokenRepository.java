package com.angio.angiobackend.api.security.repository;

import com.angio.angiobackend.api.security.entity.Token;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String>, JpaSpecificationExecutor<Token> {

    @Override
    @EntityGraph(value = "graph.Order.user", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Token> findById(String s);
}
