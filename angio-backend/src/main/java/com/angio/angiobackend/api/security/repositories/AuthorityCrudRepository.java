package com.angio.angiobackend.api.security.repositories;

import com.angio.angiobackend.api.security.entities.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityCrudRepository extends CrudRepository<AuthorityEntity, Long> {
}
