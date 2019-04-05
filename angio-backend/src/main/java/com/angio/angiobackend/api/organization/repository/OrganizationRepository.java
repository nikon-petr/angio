package com.angio.angiobackend.api.organization.repository;

import com.angio.angiobackend.api.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
