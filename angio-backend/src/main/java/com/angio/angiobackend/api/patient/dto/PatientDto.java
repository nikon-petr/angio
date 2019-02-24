package com.angio.angiobackend.api.patient.dto;

import com.angio.angiobackend.api.common.dto.FullNameDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @ApiModelProperty("Patient id")
    private Long id;

    /**
     * Patient full name.
     */
    @Valid
    @ApiModelProperty("Patient full name")
    private FullNameDto fullName;

    /**
     * Email.
     */
    @NotNull(message = "{validation.patient.email.NotNull}")
    @ApiModelProperty(value = "Patient email", example = "kozlito@yandex.ru")
    private String email;

    /**
     * Phone.
     */
    @NotNull(message = "{validation.patient.phone.NotNull}")
    @ApiModelProperty(value = "Patient phone", example = "89699389098")
    private String phone;

    /**
     * Patient date of birth.
     */
    @NotNull(message = "{validation.patient.bday.NotNull}")
    @ApiModelProperty(value = "Patient date of birth", example = "1992-10-10T00:00:00.000Z")
    private Date bday;

    /**
     * Patient locationAddress.
     */
    @NotNull(message = "{validation.patient.locationAddress.NotNull}")
    @ApiModelProperty(value = "Patient locationAddress", example = "Саратов, ул. Чернышевского 240, 1")
    private String locationAddress;

    /**
     * Work.
     */
    @NotNull(message = "{validation.patient.workAddress.NotNull}")
    @ApiModelProperty(value = "Work", example = "Саратов, ул. Степана Разина 8")
    private String workAddress;

    /**
     * Patient insurance policy number.
     */
    @NotNull(message = "{validation.patient.policy.NotNull}")
    @ApiModelProperty(value = "Patient insurance policy number", example = "1276215467890790")
    private String policy;
}
