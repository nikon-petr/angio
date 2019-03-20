package com.angio.angiobackend.api.common.report.service.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.report.exception.ReportException;
import com.angio.angiobackend.api.common.report.service.ReportService;
import com.angio.angiobackend.api.notification.exception.NotificationException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final AngioBackendProperties props;
    private final Configuration freeMarkerConfig;
    private final DynamicLocaleMessageSourceAccessor msa;

    @Override
    public byte[] render(@NonNull String template, @NonNull Object dataModel) {

        String processedHtml = processReportBody(dataModel, template);
        try (ByteArrayOutputStream os =  new ByteArrayOutputStream()) {

            String staticFilesPath = "file://" + new File(props.getUploadDirectory()).getAbsolutePath() + "/";

            log.debug("render() - set base dir: {}", staticFilesPath);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml, staticFilesPath);
            renderer.getFontResolver().addFont("fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os, false);
            renderer.finishPDF();

            return os.toByteArray();
        } catch (DocumentException | IOException e) {
            throw new ReportException(msa.getMessage("errors.api.report.creatingReportFailed"), e);
        }
    }

    private String processReportBody(Object dataModel, String templateName) {
        try {
            Template template = freeMarkerConfig.getTemplate("report/root.ftl");
            Map<String, Object> root = new HashMap<>();
            root.put("data", dataModel);
            root.put("contentTemplate", templateName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
        } catch (TemplateException e) {
            throw new NotificationException(msa.getMessage("errors.api.notification.templateProcessError", new Object[] {templateName}), e);
        } catch (IOException e) {
            throw new NotificationException(msa.getMessage("errors.api.notification.templateNotFound", new Object[] {templateName}), e);
        }
    }
}
