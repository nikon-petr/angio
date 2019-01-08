package com.angio.angiobackend.security.repositories;

import com.angio.angiobackend.security.entities.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityCrudRepository extends CrudRepository<AuthorityEntity, Long> {
}
