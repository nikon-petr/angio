package com.angio.app.security.repositories;

import com.angio.app.security.entities.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityCrudRepository extends CrudRepository<AuthorityEntity, Long> {
}
