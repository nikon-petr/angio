package com.angio.angiobackend.api.common.service;

import com.angio.angiobackend.api.common.dto.AbstractEmailDto;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface TemplateService {

    String processEmail(AbstractEmailDto emailDto, String templateName) throws IOException, TemplateException;

    String processPlainText(Object plainTextDto, String templateName) throws IOException, TemplateException;
}
