package com.angio.angiobackend.api.organization.service.impl;

import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.organization.dto.OrganizationDto;
import com.angio.angiobackend.api.organization.entity.Organization;
import com.angio.angiobackend.api.organization.mapper.OrganizationMapper;
import com.angio.angiobackend.api.organization.repository.OrganizationRepository;
import com.angio.angiobackend.api.organization.service.OrganizationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final DynamicLocaleMessageSourceAccessor msa;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ORGANIZATION_CREATE')")
    public OrganizationDto create(@NonNull OrganizationDto dto) {

        log.debug("create() - map dto to entity");
        Organization entity = organizationMapper.toEntity(dto);

        log.debug("create() save organization to data base");
        entity = organizationRepository.save(entity);

        log.debug("create() - map saved entity to dto");
        return organizationMapper.toDto(entity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ORGANIZATION_VIEW')")
    public OrganizationDto getOrganizationId(@NonNull Long id) {
        return organizationMapper.toDto(organizationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                msa.getMessage("errors.api.organization.notFound", new Object[] {id}))));
    }
}
