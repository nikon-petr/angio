package com.angio.angiobackend.api.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public abstract class AbstractEmailDto {

    private Date copyrightDate = new Date();

    private String preview = "";

    private String title = "";
}
