package com.angio.angiobackend.api.analyse.service;

import com.angio.angiobackend.api.analyse.AnalyseActions;
import com.angio.angiobackend.api.analyse.dto.AdditionalInfoDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.dto.DetailedAnalyseDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface AnalyseService {

    DetailedAnalyseDto createAnalyse(@NonNull DetailedAnalyseDto dto);

    void saveExecutedAnalyse(@NonNull AnalyseJmsDto dto);

    Page<AnalyseShortItemDto> filterAnalysesByQueryString(String queryString, Date date, Pageable pageable);

    DetailedAnalyseDto getAnalyseById(@NonNull Long id);

    DetailedAnalyseDto deleteAnalyse(@NonNull Long id);

    DetailedAnalyseDto executeAction(@NonNull Long id, @NonNull AnalyseActions action);

    DetailedAnalyseDto updateAnalyseAdditionalInfo(@NonNull Long id, @NonNull AdditionalInfoDto dto);

    DetailedAnalyseDto deleteGeometricAnalyseVessel(@NonNull Long analyseId, @NonNull Long vesselId);
}
