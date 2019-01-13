package com.angio.analyseexecutor.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullNameDto {

    private String firstname;
    private String lastname;
    private String patronymic;
}
