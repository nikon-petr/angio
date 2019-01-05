package com.angio.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Angio REST API")
                .description("Back-end of angio service")
                .contact(new Contact(
                        "Nikon Petrunin",
                        "https://github.com/nikon-petr/angio",
                        "p.nikwv@gmail.com"))
                .version("1.0")
                .build();
    }

    @Bean
    public Docket analyseApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("analyse")
                .select()
                .apis(basePackage("com.angio.server.analyse"))
                .build()
                .apiInfo(apiInfo());
    }
}