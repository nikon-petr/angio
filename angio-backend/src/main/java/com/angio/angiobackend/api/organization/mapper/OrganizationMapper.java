package com.angio.angiobackend.api.organization.mapper;

import com.angio.angiobackend.api.common.mapper.AbstractMapper;
import com.angio.angiobackend.api.organization.dto.OrganizationDto;
import com.angio.angiobackend.api.organization.entity.Organization;
import org.mapstruct.Mapper;

@Mapper
public interface OrganizationMapper extends AbstractMapper<Organization, OrganizationDto> {
}
