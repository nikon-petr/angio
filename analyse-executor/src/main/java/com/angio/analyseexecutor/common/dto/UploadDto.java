package com.angio.analyseexecutor.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UploadDto implements Serializable {

    /**
     * Upload id.
     */
    private Long id;

    /**
     * File local or external uri.
     */
    private String url;

    /**
     * File name.
     */
    private String filename;

    public static UploadDto of(String filename) {

        return new UploadDto()
                .setFilename(filename);
    }
}
