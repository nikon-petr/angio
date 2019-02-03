package com.angio.angiobackend.config;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

import static java.lang.String.format;

@AllArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    private final AngioBackendProperties props;
    private final Environment environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(format("/%s*",props.getUploadPath()))
                .addResourceLocations(format("file:%s", props.getUploadDirectory()));
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("PUT", "DELETE", "POST", "GET")
                .maxAge(3600);
    }

    @Bean
    public UriComponents uploadsUriBuilder() throws UnknownHostException {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(InetAddress.getLocalHost().getCanonicalHostName())
                .port(environment.getProperty("server.port"))
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
