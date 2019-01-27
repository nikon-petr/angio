package com.angio.angiobackend.api.analyse.type;

import java.io.Serializable;

public enum AnalyseStatusType implements Serializable {
    CREATED,
    IN_PROGRESS,
    SUCCESS,
    FAILED,
    DELETED;

    private static final long serialVersionUID = -8097058374511448671L;
}
