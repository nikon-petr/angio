package com.angio.angiobackend.api.uploads.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class StaticFileDto implements Serializable {

    private static final long serialVersionUID = 9009631838260591957L;

    /**
     * Upload id.
     */
    @ApiModelProperty("Static file id")
    private Long id;

    /**
     * URL of the static file.
     */
    @ApiModelProperty(value = "URL of the static file", accessMode = READ_ONLY)
    private String url;

    /**
     * Upload filename.
     */
    private String filename;
}
