package com.angio.angiobackend.api.patient.validation.group;

import com.angio.angiobackend.api.common.validation.group.NewFullName;

import javax.validation.groups.Default;

public interface NewPatient extends Default, NewFullName {
}
