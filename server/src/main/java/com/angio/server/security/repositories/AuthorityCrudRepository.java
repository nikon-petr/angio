package com.angio.server.security.repositories;

import com.angio.server.security.entities.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityCrudRepository extends CrudRepository<AuthorityEntity, Long> {
}
