package com.angio.angiobackend.config;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.CacheControl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AngioBackendProperties props;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(props.getUploadPath() + "*")
                .addResourceLocations("file:" + props.getUploadDirectory())
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler(props.getFrontendDistPath() + "css/*")
                .addResourceLocations("file:" + props.getFrontendDistDirectory() + "css/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler(props.getFrontendDistPath() + "img/*")
                .addResourceLocations("file:" + props.getFrontendDistDirectory() + "img/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler(props.getFrontendDistPath() + "js/*")
                .addResourceLocations("file:" + props.getFrontendDistDirectory() + "js/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler(props.getFrontendDistPath() + "favicon.ico")
                .addResourceLocations("file:" + props.getFrontendDistDirectory() + "favicon.ico")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler(props.getFrontendDistPath() + "index.html")
                .addResourceLocations("file:" + props.getFrontendDistDirectory() + "index.html")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:index.html");
    }

    @Bean
    public UriComponents uploadsUriBuilder() {
        return UriComponentsBuilder.fromHttpUrl(props.getBaseUrl())
                .path(props.getUploadPath() + "{file-name}").build();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("classpath:messages");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(new Locale("en", "US"));
        resolver.setSupportedLocales(Arrays.asList(new Locale("en", "US"), new Locale("ru", "RU")));
        return resolver;
    }

    @Bean
    public DynamicLocaleMessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        return new DynamicLocaleMessageSourceAccessor(messageSource);
    }
}
