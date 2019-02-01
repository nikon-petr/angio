package com.angio.angiobackend.config;

import com.angio.angiobackend.AngioApplication;
import com.angio.angiobackend.AngioBackendProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
@AllArgsConstructor
public class SwaggerConfig {

    private final AngioBackendProperties props;

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
                .apis(basePackage("com.angio.angiobackend.api.analyse"))
                .build()
                .globalResponseMessage(RequestMethod.GET, getGetResponses())
                .globalResponseMessage(RequestMethod.POST, getPostResponses())
                .globalResponseMessage(RequestMethod.PUT, getPutResponses())
                .globalResponseMessage(RequestMethod.DELETE, getDeleteResponses())
                .apiInfo(apiInfo())
                .securityContexts(securityContexts())
                .securitySchemes(apiKey());
    }

    @Bean
    public Docket patientApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("patient")
                .select()
                .apis(basePackage("com.angio.angiobackend.api.patient"))
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(apiKey());
    }

    @Bean
    public Docket securityApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("security")
                .select()
                .apis(basePackage("com.angio.angiobackend.api.security"))
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(apiKey());
    }

    @Bean
    public Docket uploadsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("uploads")
                .select()
                .apis(basePackage("com.angio.angiobackend.api.uploads"))
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(apiKey());
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

    private List<ResponseMessage> getCommonResponses() {
        ModelRef errorModel = new ModelRef("RestApiExceptionModel");
        return Arrays.asList(
                new ResponseMessageBuilder().code(400).message("Client error").build(),
                new ResponseMessageBuilder().code(401).message("Unauthorized").build(),
                new ResponseMessageBuilder().code(403).message(
                        "Client is not permitted access to the resource").build(),
                new ResponseMessageBuilder().code(404).message("Resource not found").build(),
                new ResponseMessageBuilder().code(500).message("Server error").build());
    }

    private List<ResponseMessage> getGetResponses() {
        List<ResponseMessage> getResponses =  new ArrayList<>();
        getResponses.add(new ResponseMessageBuilder().code(200).message("Successful").build());
        getResponses.addAll(getCommonResponses());
        return getResponses;
    }

    private List<ResponseMessage> getPostResponses() {
        List<ResponseMessage> postResponses = new ArrayList<>();
        postResponses.add(new ResponseMessageBuilder().code(200).message("Successful").build());
        postResponses.add(new ResponseMessageBuilder().code(201).message("Successful created").build());
        postResponses.addAll(getCommonResponses());
        return postResponses;
    }

    private List<ResponseMessage> getPutResponses() {
        List<ResponseMessage> putResponses = new ArrayList<>();
        putResponses.add(new ResponseMessageBuilder().code(200).message("Successful updated").build());
        putResponses.addAll(getCommonResponses());
        return putResponses;
    }

    private List<ResponseMessage> getDeleteResponses() {
        List<ResponseMessage> deleteResponses = new ArrayList<>();
        deleteResponses.add(new ResponseMessageBuilder().code(200).message("Successful deleted").build());
        deleteResponses.addAll(getCommonResponses());
        return deleteResponses;
    }

    private List<ApiKey> apiKey() {
        return Collections.singletonList(new ApiKey("JWT", props.getJwt().getHeader(), "header"));
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