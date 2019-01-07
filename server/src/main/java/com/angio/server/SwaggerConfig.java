package com.angio.server;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
@AllArgsConstructor
public class SwaggerConfig {

    private final AngioAppProperties properties;

    public static final String[] SWAGGER_SERVICE_PATHS = {
            "/webjars/**",
            "/swagger-resources/**",
            "/configuration/**",
            "/v2/api-docs",
            "/swagger-ui.html"
    };

    @Bean
    public Docket analyseApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("analyse")
                .select()
                .apis(basePackage("com.angio.server.analyse"))
                .build()
                .apiInfo(apiInfo())
                .securityContexts(securityContexts())
                .securitySchemes(apiKey());
    }

    @Bean
    public Docket securityApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("security")
                .select()
                .apis(basePackage("com.angio.server.security"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Angio REST API")
                .description("Back-end of angio service")
                .contact(new Contact(
                        "Angio Team",
                        "https://github.com/nikon-petr/angio",
                        "p.nikwv@gmail.com"))
                .version(AngioApplication.class.getPackage().getImplementationVersion())
                .build();
    }

    private List<ApiKey> apiKey() {
        return Collections.singletonList(new ApiKey("JWT", properties.getJwt().getHeader(), "header"));
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/api/.*"))
                .build());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }
}