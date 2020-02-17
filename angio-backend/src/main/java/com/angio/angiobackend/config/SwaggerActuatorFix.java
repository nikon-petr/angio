package com.angio.angiobackend.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriTemplate;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SwaggerActuatorFix {

    @Bean
    public OperationBuilderPlugin operationBuilderPluginForCorrectingActuatorEndpoints(final TypeResolver typeResolver) {
        return new OperationBuilderPluginForCorrectingActuatorEndpoints(typeResolver);
    }

    private static class OperationBuilderPluginForCorrectingActuatorEndpoints implements OperationBuilderPlugin {

        private final TypeResolver typeResolver;

        OperationBuilderPluginForCorrectingActuatorEndpoints(final TypeResolver typeResolver) {
            this.typeResolver = typeResolver;
        }

        @Override
        public void apply(final OperationContext context) {
            removeBodyParametersForReadMethods(context);
            addOperationParametersForPathParams(context);
        }

        @Override
        public boolean supports(final DocumentationType delimiter) {
            return true;
        }

        private void removeBodyParametersForReadMethods(final OperationContext context) {
            if (HttpMethod.GET.equals(context.httpMethod()) || HttpMethod.HEAD.equals(context.httpMethod())) {
                final List<Parameter> parameters = getParameters(context);
                parameters.removeIf(param -> "body".equals(param.getName()));
            }
        }

        private void addOperationParametersForPathParams(final OperationContext context) {
            final UriTemplate uriTemplate = new UriTemplate(context.requestMappingPattern());

            final List<Parameter> pathParams = uriTemplate.getVariableNames().stream()
                    .map(this::createPathParameter)
                    .collect(Collectors.toList());

            context.operationBuilder().parameters(pathParams);
        }

        private Parameter createPathParameter(final String pathParam) {
            return new ParameterBuilder()
                    .name(pathParam)
                    .description(pathParam)
                    .required(true)
                    .modelRef(new ModelRef("string"))
                    .type(typeResolver.resolve(String.class))
                    .parameterType("path")
                    .build();
        }

        @SuppressWarnings("unchecked")
        private List<Parameter> getParameters(final OperationContext context) {
            final OperationBuilder operationBuilder = context.operationBuilder();
            try {
                Field paramField = OperationBuilder.class.getDeclaredField("parameters");
                paramField.setAccessible(true);
                return (List<Parameter>) paramField.get(operationBuilder);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Unable to modify parameter field!", e);
            }
        }
    }
}
