package com.angio.analyseexecutor.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullNameDto implements Serializable {

    private static final long serialVersionUID = 7628488078354439386L;

    private String firstname;
    private String lastname;
    private String patronymic;
}
