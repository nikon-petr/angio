package com.angio.angiobackend.api.common.accessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Locale;

@Slf4j
public class DynamicLocaleMessageSourceAccessor extends MessageSourceAccessor {

    public DynamicLocaleMessageSourceAccessor(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    protected Locale getDefaultLocale() {
        return LocaleContextHolder.getLocale();
    }
}
