package com.angio.angiobackend;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
}
