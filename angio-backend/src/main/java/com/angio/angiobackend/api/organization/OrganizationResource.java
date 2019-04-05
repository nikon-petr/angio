package com.angio.angiobackend.api.organization;

import com.angio.angiobackend.api.organization.dto.OrganizationDto;
import com.angio.angiobackend.api.organization.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "Organization", description = "Organization REST API")
@RequestMapping(path = "/api/v2/organization")
public class OrganizationResource {

    private final OrganizationService organizationService;

    @ApiOperation("Create organization")
    @PostMapping
    public OrganizationDto createOrganization(@Validated OrganizationDto dto) {
        return organizationService.create(dto);
    }

    @ApiModelProperty("Get organization by id")
    @GetMapping("/{id}")
    public OrganizationDto getOrganizationById(@PathVariable Long id) {
        return organizationService.getOrganizationId(id);
    }
}
