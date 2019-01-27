package com.angio.angiobackend.api.patient;

import com.angio.angiobackend.api.patient.dto.PatientDto;
import com.angio.angiobackend.api.patient.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(description = "Patient resources")
@RequestMapping(path = "/api/v2/patient")
public class PatientController {

    private final PatientService patientService;

    @ApiOperation("Resource to get patient by policy")
    @GetMapping
    public PatientDto getPatientByPolicy(@RequestParam String policy) {
        return patientService.getPatientByPolicy(policy);
    }
}
