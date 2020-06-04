package com.angio.angiobackend.api.analyse.service;

import com.angio.angiobackend.api.analyse.dto.DetailedAnalyseDto;

public interface AnalyseZipService {
    byte[] createArchive(DetailedAnalyseDto dto);
}
