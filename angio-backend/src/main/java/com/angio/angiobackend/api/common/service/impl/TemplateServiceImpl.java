package com.angio.angiobackend.api.common.service.impl;

import com.angio.angiobackend.api.common.service.TemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
@Service
public class TemplateServiceImpl implements TemplateService {

    private final Configuration freeMarkerConfig;

    @Override
    public String process(Object dto, String templateName) throws IOException, TemplateException {
        Template template = freeMarkerConfig.getTemplate(templateName + ".ftl");
        Map<String, Object> root = Collections.singletonMap("data", dto);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
    }
}
