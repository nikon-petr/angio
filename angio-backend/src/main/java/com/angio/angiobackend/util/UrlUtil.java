package com.angio.angiobackend.util;

import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class UrlUtil {

    public static final String ANALYSE_IMAGE_CONTEXT_PATH = "images/analyses/";

    public static String getBaseUrl(HttpServletRequest request){
        return String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
    }
}
