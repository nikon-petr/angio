package com.angio.angiobackend.api.analyse.service;

import com.angio.angiobackend.api.analyse.dto.AnalyseDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.dto.ConclusionDto;
import com.angio.angiobackend.api.analyse.dto.ExtendedAnalyseDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface AnalyseService {

    ExtendedAnalyseDto createAnalyse(@NonNull ExtendedAnalyseDto dto);

    void saveExecutedAnalyse(@NonNull AnalyseDto dto);

    Page<AnalyseShortItemDto> filterAnalysesByQueryString(String queryString, Date date, Pageable pageable);

    ExtendedAnalyseDto getAnalyseById(@NonNull Long id);

    ExtendedAnalyseDto deleteAnalyse(@NonNull Long id);

    ExtendedAnalyseDto sendAnalyseToExecution(@NonNull Long id);

    ExtendedAnalyseDto patchAnalyse(@NonNull Long id, @NonNull ConclusionDto dto);

    ExtendedAnalyseDto deleteGeometricAnalyseVessel(@NonNull Long analyseId, @NonNull Long vesselId);
}
