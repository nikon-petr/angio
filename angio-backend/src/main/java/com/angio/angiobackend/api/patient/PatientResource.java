package com.angio.angiobackend.api.patient;

import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.patient.service.PatientService;
import com.angio.angiobackend.api.patient.validation.group.NewPatient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "Patient", description = "Patient REST API")
@RequestMapping(path = "/api/v2/patient")
public class PatientResource {

    private final PatientService patientService;

    @ApiOperation("Create patient")
    @PostMapping
    public PatientDto createPatient(@RequestBody @Validated(NewPatient.class) PatientDto dto) {
        return patientService.createPatient(dto);
    }

    @ApiOperation(value = "Filter patients by query string")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "format: property,asc|desc")})
    @GetMapping
    public Page<PatientDto> filterPatients(
            @RequestParam(value = "search", required = false) String search,
            @ApiIgnore @PageableDefault Pageable pageable) {
        return patientService.filterPatientsByQueryString(search, pageable);
    }

    @ApiOperation("Get patient by id")
    @GetMapping("/{id}")
    public PatientDto getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @ApiOperation("Update one or more patient fields")
    @PatchMapping
    public PatientDto updatePatient(@RequestBody @Validated PatientDto dto, Long id) {
        return patientService.updatePatient(dto, id);
    }
}
