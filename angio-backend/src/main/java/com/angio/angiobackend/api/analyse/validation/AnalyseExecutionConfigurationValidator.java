package com.angio.angiobackend.api.analyse.validation;

import com.angio.angiobackend.api.analyse.dto.ExecutionConfigurationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AnalyseExecutionConfigurationValidator
        implements ConstraintValidator<AnalyseExecutionConfigurationNonConflict, ExecutionConfigurationDto> {

    @Override
    public void initialize(AnalyseExecutionConfigurationNonConflict constraintAnnotation) {
    }

    @Override
    public boolean isValid(ExecutionConfigurationDto value, ConstraintValidatorContext context) {
        if (checkMacula(value)) {
            return true;
        }

        if (checkOpticDisk(value)) {
            return true;
        }

        if (checkProfile(value)) {
            return true;
        }

        return false;
    }

    private boolean checkMacula(ExecutionConfigurationDto value) {
        if (Boolean.TRUE.equals(value.getMaculaBloodFlow())
                || Boolean.TRUE.equals(value.getGeometric())) {
            return Boolean.FALSE.equals(value.getOpticDiskBloodFlow())
                    && Boolean.FALSE.equals(value.getProfileCysticVolume())
                    && Boolean.FALSE.equals(value.getProfileRetinalPositiveExtremum());
        } else {
            return false;
        }
    }

    private boolean checkOpticDisk(ExecutionConfigurationDto value) {
        if (Boolean.TRUE.equals(value.getOpticDiskBloodFlow())) {
            return Boolean.FALSE.equals(value.getMaculaBloodFlow())
                    && Boolean.FALSE.equals(value.getGeometric())
                    && Boolean.FALSE.equals(value.getProfileCysticVolume())
                    && Boolean.FALSE.equals(value.getProfileRetinalPositiveExtremum());
        } else {
            return false;
        }
    }

    private boolean checkProfile(ExecutionConfigurationDto value) {
        if (Boolean.TRUE.equals(value.getProfileCysticVolume()
                || Boolean.TRUE.equals(value.getProfileRetinalPositiveExtremum()))) {
            return Boolean.FALSE.equals(value.getMaculaBloodFlow())
                    && Boolean.FALSE.equals(value.getGeometric())
                    && Boolean.FALSE.equals(value.getOpticDiskBloodFlow());
        } else {
            return false;
        }
    }
}
