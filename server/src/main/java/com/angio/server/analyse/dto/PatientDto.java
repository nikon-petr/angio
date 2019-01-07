package com.angio.server.analyse.dto;

import com.angio.server.common.dto.FullNameDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto implements Serializable {

    /**
     * Patient id.
     */
    @ApiModelProperty("Patient id")
    private Long id;

    /**
     * Patient full name.
     */
    @ApiModelProperty("Patient full name")
    private FullNameDto fullName;

    /**
     * Email.
     */
    @ApiModelProperty(value = "Patient email", example = "kozlito@yandex.ru")
    private String email;

    /**
     * Phone.
     */
    @ApiModelProperty(value = "Patient phone", example = "89699389098")
    private String phone;

    /**
     * Patient date of birth.
     */
    @ApiModelProperty(value = "Patient date of birth", example = "1992-10-10T00:00:00.000Z")
    private Date bday;

    /**
     * Patient locationAddress.
     */
    @ApiModelProperty(value = "Patient locationAddress", example = "Саратов, ул. Чернышевского 240, 1")
    private String locationAddress;

    /**
     * Work.
     */
    @ApiModelProperty(value = "Work", example = "Саратов, ул. Степана Разина 8")
    private String workAddress;

    /**
     * Patient insurance policy number.
     */
    @ApiModelProperty(value = "Patient insurance policy number", example = "1276215467890790")
    private String policy;

    /**
     * Comment about patient.
     */
    @ApiModelProperty(value = "Comment about patient", example = "null")
    private String comment;
}
