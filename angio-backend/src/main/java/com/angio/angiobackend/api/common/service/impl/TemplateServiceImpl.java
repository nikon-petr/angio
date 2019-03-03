package com.angio.angiobackend.api.common.service.impl;

import com.angio.angiobackend.api.common.dto.AbstractEmailDto;
import com.angio.angiobackend.api.common.service.TemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class TemplateServiceImpl implements TemplateService {

    private final static String TEMPLATE_EXT = ".ftl";
    private final static String PLAIN_TEXT_TEMPLATE_PREFIX = "plain/";
    private final static String EMAIL_TEMPLATE_PREFIX = "email/";
    private final static String ROOT_TEMPLATE = EMAIL_TEMPLATE_PREFIX + "root" + TEMPLATE_EXT;

    private final Configuration freeMarkerConfig;

    /**
     * Process email with given dto and template name.
     *
     * @param emailDto email data model
     * @param templateName email template name without prefix and extension
     * @return email string
     */
    @Override
    public String processEmail(AbstractEmailDto emailDto, String templateName) throws IOException, TemplateException {
        Template template = freeMarkerConfig.getTemplate(ROOT_TEMPLATE);
        Map<String, Object> root = new HashMap<>();
        root.put("data", emailDto);
        root.put("contentTemplate", templateName + TEMPLATE_EXT);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
    }

    /**
     * Process plain text with given dto and template name.
     *
     * @param plainTextDto data model
     * @param templateName plain text template name without prefix and extension
     * @return plain text string
     */
    @Override
    public String processPlainText(Object plainTextDto, String templateName) throws IOException, TemplateException {
        Template template = freeMarkerConfig.getTemplate(PLAIN_TEXT_TEMPLATE_PREFIX + templateName + TEMPLATE_EXT);
        Map<String, Object> root = new HashMap<>();
        root.put("data", plainTextDto);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
    }
}
