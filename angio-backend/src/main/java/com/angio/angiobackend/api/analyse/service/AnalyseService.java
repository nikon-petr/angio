package com.angio.angiobackend.api.analyse.service;

import com.angio.angiobackend.api.analyse.AnalyseActions;
import com.angio.angiobackend.api.analyse.dto.AdditionalInfoDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.dto.DetailedAnalyseDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface AnalyseService {

    @Transactional
    DetailedAnalyseDto createAnalyse(@NonNull DetailedAnalyseDto dto);

    @Transactional
    void saveExecutedAnalyse(@NonNull AnalyseJmsDto dto);

    @Transactional(readOnly = true)
    Page<AnalyseShortItemDto> filterAnalysesByQueryString(String queryString, Date date, Pageable pageable);

    @Transactional(readOnly = true)
    DetailedAnalyseDto getAnalyseById(@NonNull Long id);

    @Transactional
    DetailedAnalyseDto deleteAnalyse(@NonNull Long id);

    @Transactional
    DetailedAnalyseDto executeAction(@NonNull Long id, @NonNull AnalyseActions action);

    @Transactional
    DetailedAnalyseDto updateAnalyseAdditionalInfo(@NonNull Long id, @NonNull AdditionalInfoDto dto);

    @Transactional
    DetailedAnalyseDto deleteGeometricAnalyseVessel(@NonNull Long analyseId, @NonNull Long vesselId);
}
