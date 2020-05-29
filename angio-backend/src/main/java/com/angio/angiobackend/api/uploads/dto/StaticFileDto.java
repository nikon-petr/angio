package com.angio.angiobackend.api.uploads.dto;

import com.angio.angiobackend.api.uploads.type.FileType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class StaticFileDto implements Serializable {

    private static final long serialVersionUID = 9009631838260591957L;

    /**
     * Upload id.
     */
    @NotNull
    @Positive
    @ApiModelProperty("Static file id")
    private Long id;

    /**
     * URL of the static file.
     */
    @ApiModelProperty(value = "URL of the static file", readOnly = true)
    private String url;

    /**
     * Upload filename.
     */
    @ApiModelProperty(value = "name of the static file", readOnly = true)
    private String filename;

    /**
     * Upload file type.
     */
    @ApiModelProperty(value = "type of the static file", readOnly = true)
    private FileType type;
}
