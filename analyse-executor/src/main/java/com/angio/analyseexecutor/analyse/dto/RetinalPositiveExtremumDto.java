package com.angio.analyseexecutor.analyse.dto;

import com.angio.analyseexecutor.uploads.dto.StaticFileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RetinalPositiveExtremumDto implements Serializable {

    private Long id;

    private StaticFileDto profileImage;

    private StaticFileDto angiogramImage;

    private Double extremumValue;

}
