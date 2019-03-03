package com.angio.angiobackend.api.common.service;

import freemarker.template.TemplateException;

import java.io.IOException;

public interface TemplateService {

    String process(Object dto, String templateName) throws IOException, TemplateException;
}
