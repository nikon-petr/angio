package com.angio.analyseexecutor.uploads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class StaticFileDto implements Serializable {

    private static final long serialVersionUID = 9009631838260591957L;

    /**
     * Upload id.
     */
    private Long id;

    /**
     * File local or external uri.
     */
    private String uri;

    /**
     * File name.
     */
    private String filename;

    public static StaticFileDto of(String filename) {

        return new StaticFileDto()
                .setFilename(filename);
    }
}
