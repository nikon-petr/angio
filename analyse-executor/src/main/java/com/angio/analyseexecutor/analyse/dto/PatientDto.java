package com.angio.analyseexecutor.analyse.dto;

import com.angio.analyseexecutor.common.dto.FullNameDto;
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
    private Long id;

    /**
     * Patient full name.
     */
    private FullNameDto fullName;

    /**
     * Email.
     */
    private String email;

    /**
     * Phone.
     */
    private String phone;

    /**
     * Patient date of birth.
     */
    private Date bday;

    /**
     * Patient locationAddress.
     */
    private String locationAddress;

    /**
     * Work.
     */
    private String workAddress;

    /**
     * Patient insurance policy number.
     */
    private String policy;

    /**
     * Comment about patient.
     */
    private String comment;
}
