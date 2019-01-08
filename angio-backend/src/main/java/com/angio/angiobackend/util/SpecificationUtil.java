package com.angio.angiobackend.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SpecificationUtil {

    /**
     * Convert string to sql like pattern for substring matching.
     *
     * e.g. "Alex" -> "%Alex%"
     *
     * @param str string for convert
     * @return substring pattern
     */
    public static String substringPattern(String str) {
        return "%" + str + "%";
    }
}
