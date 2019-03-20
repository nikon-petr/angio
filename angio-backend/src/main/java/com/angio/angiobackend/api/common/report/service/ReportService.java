package com.angio.angiobackend.api.common.report.service;

import lombok.NonNull;

public interface ReportService {
    byte[] render(@NonNull String template, @NonNull Object dataModel);
}
