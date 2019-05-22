package com.angio.angiobackend.api.organization.service;

import com.angio.angiobackend.api.organization.dto.OrganizationDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface OrganizationService {

    @Transactional
    OrganizationDto create(@NonNull OrganizationDto dto);

    @Transactional
    OrganizationDto getOrganizationId(@NonNull Long id);

    @Transactional(readOnly = true)
    Page<OrganizationDto> filterOrganizationsByQueryString(String search, Pageable pageable);
}
