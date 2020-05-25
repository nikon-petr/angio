package com.angio.angiobackend.api.analyse.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AnalyseExecutionConfigurationValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnalyseExecutionConfigurationNonConflict {
    String message() default "{validation.analyse.executionConfiguration.AnalyseExecutionConfigurationNonConflict}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
