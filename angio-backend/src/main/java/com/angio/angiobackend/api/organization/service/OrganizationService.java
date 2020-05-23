package com.angio.angiobackend.api.organization.service;

import com.angio.angiobackend.api.organization.dto.OrganizationLockedDto;
import com.angio.angiobackend.api.organization.dto.OrganizationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationService {

    OrganizationDto create(OrganizationDto dto);

    OrganizationDto getOrganizationId(Long id);

    Page<OrganizationDto> filterOrganizationsByQueryString(String search, Pageable pageable);

    OrganizationDto changeOrganizationLocked(Long id, OrganizationLockedDto dto);
}
