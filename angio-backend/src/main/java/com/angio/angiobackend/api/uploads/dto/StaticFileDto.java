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
    @ApiModelProperty("Upload id")
    private Long id;

    /**
     * Upload local or external uri.
     */
    @ApiModelProperty(value = "Upload uri", accessMode = READ_ONLY)
    private String uri;

    /**
     * Upload filename.
     */
    @ApiModelProperty(value = "Upload file name", accessMode = READ_ONLY)
    private String filename;
}
