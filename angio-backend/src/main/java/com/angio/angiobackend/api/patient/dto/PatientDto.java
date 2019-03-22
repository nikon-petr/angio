package com.angio.angiobackend.api.patient.dto;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import com.angio.angiobackend.api.patient.validation.group.NewPatient;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto implements Serializable {

    private static final long serialVersionUID = 8785049011241633265L;

    /**
     * Patient id.
     */
    @ApiModelProperty(value = "Patient id", readOnly = true)
    private Long id;

    /**
     * Patient full name.
     */
    @Valid
    @NotNull(groups = {NewPatient.class})
    @ApiModelProperty("Patient full name")
    private FullNameDto fullName;

    /**
     * Email.
     */
    @NotNull(groups = {NewPatient.class})
    @Size(min = 3, max = 30)
    @ApiModelProperty(value = "Patient email", example = "kozlito@yandex.ru")
    private String email;

    /**
     * Phone.
     */
    @Size(min = 2, max = 11)
    @NotNull(groups = {NewPatient.class}, message = "{validation.patient.phone.NotNull}")
    @ApiModelProperty(value = "Patient phone", example = "89699389098")
    private String phone;

    /**
     * Patient date of birth.
     */
    @Past
    @NotNull(groups = {NewPatient.class}, message = "{validation.patient.bday.NotNull}")
    @ApiModelProperty(value = "Patient date of birth", example = "1992-10-10T00:00:00.000Z")
    private Date bday;

    /**
     * Patient locationAddress.
     */
    @Size(min = 3, max = 100)
    @NotNull(groups = {NewPatient.class}, message = "{validation.patient.locationAddress.NotNull}")
    @ApiModelProperty(value = "Patient locationAddress", example = "Саратов, ул. Чернышевского 240, 1")
    private String locationAddress;

    /**
     * Work.
     */
    @Size(min = 3, max = 100)
    @NotNull(groups = {NewPatient.class}, message = "{validation.patient.workAddress.NotNull}")
    @ApiModelProperty(value = "Work", example = "Саратов, ул. Степана Разина 8")
    private String workAddress;

    /**
     * Patient insurance policy number.
     */
    @Size(min = 16, max = 16)
    @NotNull(groups = {NewPatient.class}, message = "{validation.patient.policy.NotNull}")
    @ApiModelProperty(value = "Patient insurance policy number", example = "1276215467890790")
    private String policy;
}
