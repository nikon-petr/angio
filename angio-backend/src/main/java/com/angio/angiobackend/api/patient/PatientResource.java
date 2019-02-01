package com.angio.angiobackend.api.patient;

import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.patient.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(description = "Patient resources")
@RequestMapping(path = "/api/v2/patient")
public class PatientResource {

    private final PatientService patientService;

    @ApiOperation("Create patient")
    @PostMapping
    public PatientDto createPatient(@RequestBody PatientDto dto) {
        return patientService.createPatient(dto);
    }

    @ApiOperation("Get patient by policy")
    @GetMapping("/{id}")
    public PatientDto getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @ApiOperation("Get patient by policy")
    @GetMapping
    public PatientDto getPatientByPolicy(@RequestParam String policy) {
        return patientService.getPatientByPolicy(policy);
    }

    @ApiOperation("Update one or more patient fields")
    @PatchMapping
    public PatientDto updatePatient(@RequestBody PatientDto dto, Long id) {
        return patientService.updatePatient(dto, id);
    }
}
